package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.service.AbsenceApplicationService;
import com.example.webapp.service.TimeRecorderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/time-recorder")
@RequiredArgsConstructor
public class TimeRecorderController {

	private final TimeRecorderService timeRecorderService;
	private final AbsenceApplicationService absenceApplicationService;
	LocalDate today = LocalDate.now();

	@GetMapping
	public String showTimeRecorder(Model model, HttpSession session) {
		List<ShiftSchedule> todaysEmployees = timeRecorderService.getEmployeeList(today);
		model.addAttribute("todaysEmployees", todaysEmployees);

		//		当日欠勤者がセッションに保存されていないとき
		if (Objects.equals(null, session.getAttribute("todayAbsences"))) {
			List<AbsenceApplication> todayAbsences = absenceApplicationService.getTodayApplications();
			session.setAttribute("todayAbsences", todayAbsences);
			model.addAttribute("todayAbsences", todayAbsences);
			return "time-recorder/top";
		}

		model.addAttribute("todayAbsences", session.getAttribute("todayAbsences"));
		return "time-recorder/top";
	}

	@PostMapping("/record")
	public String showRecordPage(@RequestParam("employee-id") Integer employeeId, Model model,
			RedirectAttributes attributes) {
		ShiftSchedule targetShift = timeRecorderService.getTodayPersonalShiftData(employeeId);
		if (targetShift != null) {
			model.addAttribute("employee", targetShift.getEmployee());
			model.addAttribute("today", targetShift.getDate());
			return "time-recorder/record";
		} else {
			attributes.addFlashAttribute("errorMessage", "シフトデータが存在しません");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/clock-in")
	public String start(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		//getTodayPersonalTimeRecordDataはいらないな
		TimeRecord targetTimeRecord = timeRecorderService.getTodayPersonalTimeRecordData(employeeId);
		if (Objects.equals(targetTimeRecord, null)) {
			timeRecorderService.clockIn(employeeId);
			model.addAttribute("message", "出勤");
			return "time-recorder/execute";
		} else {
			attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/clock-out")
	public String end(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		TimeRecord timeRecord = timeRecorderService.getTodayPersonalTimeRecordData(employeeId);
		if (Objects.equals(timeRecord, null)) {
			attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
			return "redirect:/time-recorder";
		}
		if (Objects.equals(timeRecord.getClockOut(), null)) {
			timeRecorderService.clockOut(employeeId);
			model.addAttribute("message", "退勤");
			return "time-recorder/execute";
		}
		attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");

	}
	
	@ExceptionHandler(InvalidClockException.class)
	public String redirectTimeRecorderPage(Exception e,RedirectAttributes attributes){
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/time-recorder";		
	}
}
