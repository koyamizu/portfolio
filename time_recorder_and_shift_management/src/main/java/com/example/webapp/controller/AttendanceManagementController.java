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

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/attendance")
public class AttendanceManagementController {

	private final AttendanceManagementService service;

	@GetMapping("/{targetMonth}")
	public String showAttendanceHistory(@PathVariable Integer targetMonth,
			Authentication auth,Model model) {
		List<ShiftAndTimestamp> histories;
		List<Employee> employees;
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			histories = service.selectAllHistoriesToDateByMonth(targetMonth);
			employees=histories.stream().map(ShiftAndTimestamp::getEmployee).distinct().toList();
			model.addAttribute("employees",employees);
		} else {
			Integer employeeId = Integer.parseInt(auth.getName());
			histories = service.selectHistoryToDateByEmployeeIdAndMonth(employeeId,targetMonth);
		}
		model.addAttribute("histories",histories);
		return "attendance/history";
	}
}
