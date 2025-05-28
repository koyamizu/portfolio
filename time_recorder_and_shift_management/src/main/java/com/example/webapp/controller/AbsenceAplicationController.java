package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Absence;
import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/absence-application")
public class AbsenceAplicationController {
	
	private final AbsenceAplicationService absenceApplicationService;
	private final ShiftManagementService shiftManagementService;
	
	@GetMapping
	public String showList(Model model) {
		List<AbsenceApplication> applications=absenceApplicationService.selectAllApplicationsAfterToday(LocalDate.now());
		model.addAttribute("applications", applications);
		return "absence-application/list";
	}
	
	@GetMapping("request")
	public String showForm(Model model,Authentication auth) {
		Integer employeeId=Integer.parseInt(auth.getName());
		List<ShiftSchedule> shifts=shiftManagementService.selectPersonalShiftsAfterToday(employeeId,LocalDate.now());
		List<Absence> reasons=absenceApplicationService.selectAllReasons();
		String name=auth.getName();
		AbsenceApplicationForm form=new AbsenceApplicationForm();
		model.addAllAttributes(
				Map.of(
						"shifts",shifts
						,"reasons",reasons
						,"name",name
						,"from",form
				)
		);
		return "absence-application/form";
	}
	
	@PostMapping("decision")
	public String registerDecision(@RequestParam("id") Integer shiftId,@RequestParam Boolean decision
			,RedirectAttributes attributes) {
		absenceApplicationService.updateAbsenceApproval(shiftId,decision);
		//失敗したときのメッセージもいると思う
		attributes.addFlashAttribute("message", "承認処理が完了しました");
		return "redirect:/absence-application";
	}
	
	@GetMapping("delete/{id}")
	public String deleteApplication(@PathVariable("id") Integer applicationId,RedirectAttributes attributes) {
		absenceApplicationService.delteApplication(applicationId);
		attributes.addFlashAttribute("message", "申請を削除しました");
		return "redirect:/absece-application";
	}
}
