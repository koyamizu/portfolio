package com.example.webapp.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.service.AttendanceManagementService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceManagementController {

	private final AttendanceManagementService service;

	@GetMapping("/{targetMonth}")
	public String showAttendanceHistory(@PathVariable Integer targetMonth,
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
		} else {
			Integer employeeId = Integer.parseInt(auth.getName());
			histories = service.selectHistoryToDateByEmployeeIdAndMonth(employeeId, targetMonth);
		}
		model.addAttribute("histories", histories);
		model.addAttribute("from", from);
		return "attendance/history";
	}

	@GetMapping("/{targetMonth}/{employee_id}")
	public String showPersonalAttendanceHistory(@PathVariable Integer targetMonth, @PathVariable Integer employee_id,
			Authentication auth, Model model, HttpSession session) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			List<ShiftAndTimestamp> personalHistories = service.selectHistoryToDateByEmployeeIdAndMonth(employee_id,
					targetMonth);
			List<Employee> employees = service.selectWorkedMembersByMonth(targetMonth);
			String from = (String) session.getAttribute("from");
			model.addAttribute("from", from);
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", personalHistories);
		}
		return "attendance/history";
	}
}
