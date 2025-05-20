package com.example.webapp.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/time-recorder")
@RequiredArgsConstructor
public class TimeRecorderController {

	private final TimeRecorderService service;
	LocalDate today = LocalDate.now();

	@GetMapping
	public String timeRecorder(Model model) {
		model.addAttribute("todaysMembers", service.selectEmployeesByDate(today));
		return "time-recorder/top";
	}

	@PostMapping("/stamp")
	public String stamp(@RequestParam Integer employee_id, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp shiftAndTimestamp = service.selectShiftAndTimestampByEmployeeIdAndDate(employee_id, today);
		if (shiftAndTimestamp != null) {
			model.addAttribute("employee", shiftAndTimestamp.getEmployee());
			model.addAttribute("today", shiftAndTimestamp.getDate());
			model.addAttribute("shiftId", shiftAndTimestamp.getId());
			return "time-recorder/stamp";
		} else {
			//そのIDをもつ従業員は本日出勤予定ではありません、とかでもいいかも
			attributes.addFlashAttribute("errorMessage", "シフトデータが存在しません");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/start")
	public String start(@RequestParam Integer shift_id, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp shiftAndTimestamp = service.selectShiftAndTimestampByShiftId(shift_id);
		if (shiftAndTimestamp.getStart() == null) {
			service.start(shift_id);
			model.addAttribute("message", "出勤");
			return "time-recorder/execute";
		} else {
			attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/end")
	public String end(@RequestParam Integer shift_id, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp shiftAndTimestamp = service.selectShiftAndTimestampByShiftId(shift_id);
		if (shiftAndTimestamp.getStart() == null) {
			attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
			return "redirect:/time-recorder";
		}
		if (shiftAndTimestamp.getEnd() == null) {
			service.end(shift_id);
			model.addAttribute("message", "退勤");
			return "time-recorder/execute";
		}
		
		attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");
		return "redirect:/time-recorder";

	}
}
