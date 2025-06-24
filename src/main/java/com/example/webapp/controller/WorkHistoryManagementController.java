package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.form.TimeRecordForm;
import com.example.webapp.helper.TimeRecordHelper;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.service.WorkHistoryManagementService;

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
			List<TimeRecord> histories = workHistoryManagementService
					.getAllWorkHistoriesToDate(targetMonth);
			List<Employee> employees = employeesManagementService.getAllIdAndName();
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", histories);
			session.setAttribute("fromPage", targetMonth);
		}
		return "work-history/history";
	}

	//userとadmin
	@GetMapping("{month}/{employee-id}")
	public String showPersonalWorkHistories(@PathVariable("month") Integer targetMonth,
			@PathVariable("employee-id") Integer employeeId,
			Authentication auth, Model model, RedirectAttributes attributes, HttpSession session) {
		//		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));

		session.setAttribute("fromPage", targetMonth + "/" + employeeId);
		boolean isSame = auth.getName().equals(employeeId.toString());
		if (!isSame) {
			attributes.addFlashAttribute("errorMessage", "他人の勤務履歴は閲覧できません");
			employeeId = Integer.parseInt(auth.getName());
			String fromPage=(String)session.getAttribute("fromPage");
			return "redirect:/work-history/"+fromPage;
		}

		List<TimeRecord> personalHistories = workHistoryManagementService
				.selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId,
						targetMonth);
		List<Employee> employees = employeesManagementService.getAllIdAndName();
		model.addAttribute("employees", employees);
		model.addAttribute("targetMonth", targetMonth);
		model.addAttribute("histories", personalHistories);

		return "work-history/history";

	}

	@GetMapping("edit/{target-date}/{employee-id}")
	public String showPersonalWorkHistory(@PathVariable("target-date") LocalDate targetDate,
			@PathVariable("employee-id") Integer employeeId, Model model) {
		TimeRecord targetHistoriy = workHistoryManagementService.selectWorkHistoryByEmployeeIdAndDate(employeeId,
				targetDate);
		TimeRecordForm form = TimeRecordHelper.convertTimeRecordForm(targetHistoriy);
		model.addAttribute("form", form);
		return "work-history/edit";
	}

	@PostMapping("update")
	public String updateWorkHistory(TimeRecordForm form, RedirectAttributes attribute,
			HttpSession session) {
		TimeRecord updatedHistory = TimeRecordHelper.convertTimeRecord(form);
		workHistoryManagementService.updateWorkHistory(updatedHistory);
		attribute.addFlashAttribute("message", "更新しました");
		//		Integer targetMonth=updatedHistory.getDate().getMonthValue();
		//		Integer employeeId=updatedHistory.getEmployee().getEmployeeId();
		//		return "redirect:/work-history/"+targetMonth+"/"+employeeId;
		var fromPage = session.getAttribute("fromPage");
		session.removeAttribute("fromPage");
		return "redirect:/work-history/" + fromPage;
	}
}
