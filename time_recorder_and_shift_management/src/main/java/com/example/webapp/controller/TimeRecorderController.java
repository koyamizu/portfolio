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
	public String stamp(@RequestParam Integer employeeId, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp targetShift = service.selectShiftByEmployeeIdAndDate(employeeId, today);
		if (targetShift != null) {
			model.addAttribute("employee", targetShift.getEmployee());
			model.addAttribute("today", targetShift.getDate());
			model.addAttribute("shiftId", targetShift.getId());
			return "time-recorder/stamp";
		} else {
			//そのIDをもつ従業員は本日出勤予定ではありません、とかでもいいかも
			attributes.addFlashAttribute("errorMessage", "シフトデータが存在しません");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/start")
	public String start(@RequestParam("shift-id") Integer shiftId, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp targetTimestamp = service.selectTimestampByShiftId(shiftId);
		if (targetTimestamp.getStart() == null) {
			service.updateStartTimeByShiftId(shiftId);
			model.addAttribute("message", "出勤");
			return "time-recorder/execute";
		} else {
			attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/end")
	public String end(@RequestParam("shift-id") Integer shiftId, Model model, RedirectAttributes attributes) {
		ShiftAndTimestamp targetTimestamp = service.selectTimestampByShiftId(shiftId);
		if (targetTimestamp.getStart() == null) {
			attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
			return "redirect:/time-recorder";
		}
		if (targetTimestamp.getEnd() == null) {
			service.updateEndTimeByShiftId(shiftId);
			model.addAttribute("message", "退勤");
			return "time-recorder/execute";
		}
		
		attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");
		return "redirect:/time-recorder";

	}
}
