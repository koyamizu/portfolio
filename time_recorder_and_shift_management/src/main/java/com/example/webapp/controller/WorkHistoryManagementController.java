package com.example.webapp.controller;

import java.util.List;

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
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.form.ShiftAndTimestampForm;
import com.example.webapp.service.WorkHistoryManagementService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work-history")
public class WorkHistoryManagementController {

	private final WorkHistoryManagementService service;

	@GetMapping("{targetMonth}")
	public String showWorkHistoryHistory(@PathVariable Integer targetMonth,
			Authentication auth, Model model, HttpSession session) {
		List<ShiftAndTimestamp> histories;
		List<Employee> employees;
		String from = (String) session.getAttribute("from");
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin && from.equals("admin")) {
			histories = service.selectAllHistoriesToDateByMonth(targetMonth);
			employees = histories.stream().map(ShiftAndTimestamp::getEmployee).distinct().toList();
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			session.setAttribute("fromPage",targetMonth);
		} else {
			Integer employeeId = Integer.parseInt(auth.getName());
			histories = service.selectHistoriesToDateByEmployeeIdAndMonth(employeeId, targetMonth);
		}
		model.addAttribute("histories", histories);
		model.addAttribute("from", from);
		return "work-history/history";
	}

	@GetMapping("{targetMonth}/{employee_id}")
	public String showPersonalWorkHistoryHistory(@PathVariable Integer targetMonth, @PathVariable("employee_id") Integer employeeId,
			Authentication auth, Model model, HttpSession session) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			List<ShiftAndTimestamp> personalHistories = service.selectHistoriesToDateByEmployeeIdAndMonth(employeeId,
					targetMonth);
			List<Employee> employees = service.selectWorkedMembersByMonth(targetMonth);
			String from = (String) session.getAttribute("from");
			model.addAttribute("from", from);
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", personalHistories);
			session.setAttribute("fromPage", targetMonth+"/"+employeeId);
		}
		return "work-history/history";
	}

	@GetMapping("edit/{shift_id}")
	public String showPersonalWorkHistoryHistory(@PathVariable("shift_id") Integer shiftId,Authentication auth, Model model, ShiftAndTimestampForm form) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			ShiftAndTimestamp targetHistoriy = service.selectHistoryByHistoryId(shiftId);
			model.addAttribute("history", targetHistoriy);
		}
		return "work-history/edit";
	}
	
	@PostMapping("update")
	public String updateHistory(ShiftAndTimestampForm updatedHistory,RedirectAttributes attribute,HttpSession session) {
		service.updateHistory(updatedHistory);
		var sessionName=session.getAttribute("fromPage");
		String fromPage=(sessionName instanceof Integer)? sessionName.toString():(String)sessionName;
		attribute.addFlashAttribute("message","更新しました");
//		Integer targetMonth=updatedHistory.getDate().getMonthValue();
		return "redirect:/work-history/"+fromPage;
	}
}
