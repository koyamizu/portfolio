package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/time-recorder")
@RequiredArgsConstructor
public class TimeRecorderController {

	private final TimeRecorderService service;
	LocalDate today = LocalDate.now();

	@GetMapping
	public String showTimeRecorder(Model model) {
		List<ShiftSchedule> todaysEmployees=service.selectEmployeesByDate(today);
//		model.addAttribute("today", today);
		model.addAttribute("todaysEmployees", todaysEmployees);
		return "time-recorder/top";
	}

	@PostMapping("/stamp")
	public String showStampPage(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		ShiftSchedule targetShift = service.selectByEmployeeId(employeeId);
		if (targetShift != null) {
			model.addAttribute("employee", targetShift.getEmployee());
			//↓これもフロントエンドで生成すればいいかなとか思う
//			model.addAttribute("today", today);
//			model.addAttribute("shiftId", targetShift.getShiftId());
			return "time-recorder/stamp";
		} else {
			attributes.addFlashAttribute("errorMessage", "シフトデータが存在しません");
			return "redirect:/time-recorder";
		}
	}

//	シフトデータが存在しなければ打刻ページには移れない。
//	打刻ができたということはシフトが存在しているということなので、パラメーター引数はemployee-idでいいのではないかと思う
	@PostMapping("/stamp/start")
	public String start(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		//ここでしか使わないんだったらemployeeIdだけ渡して、date=CURRENT_DATEでいい気もする
		TimeRecord timeRecord = service.selectByDateAndEmployeeId(employeeId, today);
		if (Objects.equals(timeRecord,null)) {
			service.updateStartTimeByEmployeIdAndDate(employeeId, today);
			model.addAttribute("message", "出勤");
			return "time-recorder/execute";
		} else {
			attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/end")
	public String end(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		TimeRecord timeRecord = service.selectByDateAndEmployeeId(employeeId, today);
		if (Objects.equals(timeRecord,null)) {
			attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
			return "redirect:/time-recorder";
		}
		if (Objects.equals(timeRecord.getClockOut(),null)) {
			service.updateEndTimeByEmployeeIdAndDate(employeeId, today);
			model.addAttribute("message", "退勤");
			return "time-recorder/execute";
		}
		attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");
		return "redirect:/time-recorder";

	}
}
