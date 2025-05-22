package com.example.webapp.controller;

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
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;
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
	@GetMapping("{target-month}")
	public String showWorkHistories(@PathVariable("target-month") Integer targetMonth,
			Authentication auth, Model model) {
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin) {
			List<ShiftAndTimeRecord> histories = workHistoryManagementService
					.selectAllWorkHistoriesToDateByMonth(targetMonth);
			//従業員IDと名前だけを返すマッパーメソッドを作る？　シフト編集ページのドラッグ＆ドロップ部分にも使えそう
			List<Employee> employees = employeesManagementService.selectAllIdAndName();
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", histories);
		}
		return "work-history/history";
	}

	//userとadmin
	@GetMapping("{target-month}/{employee-id}")
	public String showPersonalWorkHistories(@PathVariable("target-month") Integer targetMonth,
			@PathVariable("employee-id") Integer employeeId,
			Authentication auth, Model model, RedirectAttributes attributes) {
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin) {
			List<Employee> employees = workHistoryManagementService.selectWorkedEmployeesByMonth(targetMonth);
			model.addAttribute("employees", employees);
		} else {
			boolean isSame = auth.getName().equals(employeeId.toString());
			if (!isSame) {
				attributes.addFlashAttribute("errorMessage", "他人の勤務履歴は閲覧できません");
				return "redirect:/" + targetMonth;
			}
		}
		List<ShiftAndTimeRecord> personalHistories = workHistoryManagementService
				.selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId,
						targetMonth);
		model.addAttribute("targetMonth", targetMonth);
		model.addAttribute("histories", personalHistories);
		return "work-history/history";

	}

	@GetMapping("edit/{shift-id}")
	//SecurityConfigに/{taegetMont}/editとか設定できたらAuthenticationの引数はいらない
	public String showPersonalWorkHistory(@PathVariable("shift-id") Integer shiftId, Authentication auth, Model model,
			ShiftAndTimeRecordForm form) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			ShiftAndTimeRecord targetHistoriy = workHistoryManagementService.selectWorkHistoryByShiftId(shiftId);
			model.addAttribute("history", targetHistoriy);
		}
		return "work-history/edit";
	}

	@PostMapping("update")
	public String updateWorkHistory(ShiftAndTimeRecordForm updatedHistory, RedirectAttributes attribute,
			HttpSession session) {
		workHistoryManagementService.updateWorkHistory(updatedHistory);
//		var sessionName = session.getAttribute("fromPage");
//		String fromPage = (sessionName instanceof Integer) ? sessionName.toString() : (String) sessionName;
		attribute.addFlashAttribute("message", "更新しました");
		return "redirect:/javascript:history.back(-1)";
	}
}
