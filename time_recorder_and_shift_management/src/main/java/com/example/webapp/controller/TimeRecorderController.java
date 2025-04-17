package com.example.webapp.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/time_recorder")
@RequiredArgsConstructor
public class TimeRecorderController {

	private final TimeRecorderService timeRecorderService;
	LocalDate today=LocalDate.now();
	
	@GetMapping
	public String employees(Model model) {
		model.addAttribute("employees",timeRecorderService.selectEmployeesByDate(today));
		return "time_recorder/time_recorder";
	}
	
//	@GetMapping("/time_recorder/dakoku/{id}")
//	public String start(@PathVariable String id,Model model,RedirectAttributes attributes) {
//		ShiftAndTimestamp shiftAndTimestamp=timeRecorderService.selectShiftAndTimestampByEmployeeIdAndDate(id, today);
//		if(shiftAndTimestamp!=null) {
//			model.addAttribute("")
//		}
//	}
	
}
