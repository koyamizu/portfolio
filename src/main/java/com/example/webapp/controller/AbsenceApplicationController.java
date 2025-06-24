package com.example.webapp.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateApplicationException;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.AbsenceApplicationForm;
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
	public String showList(Model model, Authentication auth,HttpSession session) throws InvalidAccessException {
		String from = (String) session.getAttribute("from");
		Integer employeeId = Integer.parseInt(auth.getName());
		List<AbsenceApplication> applications=absenceApplicationService.get(from,employeeId);
		model.addAttribute("from", from);
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
						"shiftSchedules", shiftSchedules
						, "absenceReasons", absenceReasons
						, "employeeId", employeeId
						, "form", new AbsenceApplicationForm()));
		return "absence-application/form";
	}

	@PostMapping("decision")
	public String updateApprove(@RequestParam Integer shiftId, @RequestParam Boolean decision,
			RedirectAttributes attributes) {
		absenceApplicationService.updateApprove(shiftId, decision);
		attributes.addFlashAttribute("message", "承認処理が完了しました");
		return "redirect:/absence-application";
	}

	@PostMapping("submit")
	public String submitApplication(@Validated AbsenceApplicationForm form,BindingResult bindingResult
			, RedirectAttributes attributes) throws DuplicateApplicationException {
		if(bindingResult.hasErrors()) {
			return "redirect:/absence-application/request";
		}
		absenceApplicationService.submitApplication(form);
		attributes.addFlashAttribute("message", "申請が完了しました");
		return "redirect:/absence-application";
	}

	@GetMapping("delete/{application-id}")
	public String deleteApplication(@PathVariable("application-id") Integer applicationId, RedirectAttributes attributes) throws InvalidEditException {
		absenceApplicationService.deleteApplication(applicationId);
		attributes.addFlashAttribute("message", "申請を削除しました");
		return "redirect:/absence-application";
	}
	
	@ExceptionHandler({InvalidEditException.class})
	public String redirectToApplicationsPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/absence-application";
	}
	
	@ExceptionHandler({DuplicateApplicationException.class})
	public String redirectToRequestPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/absence-application/request";
	}
	
	@ExceptionHandler({InvalidAccessException.class})
	public String redirectToIndexPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/";
	}
}
