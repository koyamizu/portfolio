package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.TimeRecordForm;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.service.WorkHistoryManagementService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work-history")
public class WorkHistoryManagementController {

	private final WorkHistoryManagementService workHistoryManagementService;
	private final EmployeesManagementService employeesManagementService;

	//adminのみ
	@GetMapping("{month}")
	public String showWorkHistories(@PathVariable("month") Integer targetMonth,
			Authentication auth, Model model, HttpSession session) {
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin) {
			session.setAttribute("fromPage", targetMonth);
			List<TimeRecord> histories = workHistoryManagementService.getAllWorkHistoriesToDate(targetMonth);
			List<Employee> employees = employeesManagementService.getAllEmployeeIdAndName();
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", histories);
			session.setAttribute("fromPage", targetMonth);
			return "work-history/history";
		}

		Integer principalEmployeeId = Integer.parseInt(auth.getName());
		return "redirect:/work-history/" + targetMonth + "/" + principalEmployeeId;
	}

	//userとadmin
	@GetMapping("{month}/{employee-id}")
	public String showPersonalWorkHistories(@PathVariable("month") Integer targetMonth,
			@PathVariable("employee-id") Integer employeeId,
			Authentication auth, Model model, RedirectAttributes attributes, HttpSession session) {
		
		Integer principalEmployeeId = Integer.parseInt(auth.getName());
		
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		boolean isSame = principalEmployeeId.equals(employeeId);
		
		if (!isSame&&!isAdmin) {
			attributes.addFlashAttribute("errorMessage", "他人の勤務履歴は閲覧できません");
			return "redirect:/work-history/" + targetMonth+"/"+principalEmployeeId;
		}
		session.setAttribute("fromPage", targetMonth + "/" + employeeId);
		List<TimeRecord> personalHistories = workHistoryManagementService
				.gettPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId,
						targetMonth);
		List<Employee> employees = employeesManagementService.getAllEmployeeIdAndName();
		model.addAttribute("employees", employees);
		model.addAttribute("targetMonth", targetMonth);
		model.addAttribute("histories", personalHistories);

		return "work-history/history";

	}

	@GetMapping("edit/{date}/{employee-id}")
	public String showPersonalWorkHistory(@PathVariable("date") LocalDate targetDate,
			@PathVariable("employee-id") Integer employeeId, Model model, HttpSession session) throws NoDataException {
		TimeRecordForm targetHistory = workHistoryManagementService.getWorkHistoryDetailByEmployeeIdAndDate(employeeId,
				targetDate);
		session.setAttribute("editPage", "edit/"+targetDate + "/" + employeeId);
		String fromPage=session.getAttribute("fromPage").toString();
		model.addAttribute("form", targetHistory);
		model.addAttribute("fromPage", fromPage);
		return "work-history/edit";
	}

	@PostMapping("update")
	public String updateWorkHistory(@Validated TimeRecordForm form,BindingResult bindingResult, RedirectAttributes attribute,
			HttpSession session) throws InvalidEditException {
		if(bindingResult.hasErrors()) {
			String editPage=(String)session.getAttribute("editPage");
			return "redirect:/work-history/"+editPage;
		}
		workHistoryManagementService.updateWorkHistory(form);
		attribute.addFlashAttribute("message", "更新しました");
		var fromPage = session.getAttribute("fromPage");
		session.removeAttribute("fromPage");
		session.removeAttribute("editPage");
		return "redirect:/work-history/" + fromPage;
	}

	@ExceptionHandler({ InvalidEditException.class })
	public String redirectToApplicationsPage(Exception e, RedirectAttributes attributes, HttpSession session) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		String editPage = (String) session.getAttribute("editPage");
		session.removeAttribute("editPage");
		return "redirect:/work-history/" + editPage;
	}
}
