package com.example.webapp.controller;

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

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.AbsenceApplicationForm;
import com.example.webapp.helper.AbsenceApplicationHelper;
import com.example.webapp.service.AbsenceApplicationService;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/absence-application")
public class AbsenceApplicationController {

	private final AbsenceApplicationService absenceApplicationService;
	private final ShiftManagementService shiftManagementService;

	@GetMapping
	public String showList(Model model, Authentication auth) {
//		List<AbsenceApplication> applications;
//		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
//			applications = absenceApplicationService.selectAllApplicationsAfterToday();
//		} else {
//			Integer employeeId = Integer.parseInt(auth.getName());
//			applications = absenceApplicationService.selectPersonalApplicationsAfterToday(employeeId);
//		}
		List<AbsenceApplication> applications=absenceApplicationService.get(auth);
		model.addAttribute("applications", applications);
		return "absence-application/list";
	}

	@GetMapping("request")
	public String showForm(Model model, Authentication auth) {
		Integer employeeId = Integer.parseInt(auth.getName());
		List<ShiftSchedule> shiftSchedules = shiftManagementService.getAllShiftsAfterToday(employeeId);
		List<AbsenceReason> absenceReasons = absenceApplicationService.selectAllReasons();
		model.addAllAttributes(
				Map.of(
						"shiftSchedules", shiftSchedules, "absenceReasons", absenceReasons, "employeeId", employeeId, "form", new AbsenceApplicationForm()));
		return "absence-application/form";
	}

	@PostMapping("decision")
	public String registerDecision(@RequestParam Integer shiftId, @RequestParam Boolean decision,
			RedirectAttributes attributes) {
		absenceApplicationService.updateApprove(shiftId, decision);
		//失敗したときのメッセージもいると思う
		attributes.addFlashAttribute("message", "承認処理が完了しました");
		return "redirect:/absence-application";
	}

	@PostMapping("submit")
	public String insertApplication(AbsenceApplicationForm form, RedirectAttributes attributes) {
		AbsenceApplication application = AbsenceApplicationHelper.convertAbsenceApplication(form);
		absenceApplicationService.insertApplication(application);
		attributes.addFlashAttribute("message", "申請が完了しました");
		return "redirect:/absence-application";
	}

	@GetMapping("delete/{application-id}")
	public String deleteApplication(@PathVariable("application-id") Integer applicationId, RedirectAttributes attributes) {
		absenceApplicationService.deleteApplication(applicationId);
		attributes.addFlashAttribute("message", "申請を削除しました");
		return "redirect:/absence-application";
	}
}
