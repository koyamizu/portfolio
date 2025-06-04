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

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
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
		List<ShiftSchedule> todaysEmployees = timeRecorderService.selectEmployeesByDate(today);
		model.addAttribute("todaysEmployees", todaysEmployees);
		//可否にかかわらず、当日緊急欠勤する従業員名とその理由のみ表示するメソッドを挿入
		if (Objects.equals(null, session.getAttribute("todayAbsences"))) {
			List<AbsenceApplication> todayAbsences = absenceApplicationService.getTodayApplications();
			session.setAttribute("todayAbsences", todayAbsences);
			model.addAttribute("todayAbsences", todayAbsences);
			return "time-recorder/top";
		}
//		@SuppressWarnings("unchecked")
//		List<AbsenceApplication> todayAbsences = (List<AbsenceApplication>) session.getAttribute("todayAbsences");
//		model.addAttribute("todayAbsences", todayAbsences);
		model.addAttribute("todayAbsences", session.getAttribute("todayAbsences"));
		return "time-recorder/top";
	}

	@PostMapping("/stamp")
	public String showStampPage(@RequestParam("employee-id") Integer employeeId, Model model,
			RedirectAttributes attributes) {
		ShiftSchedule targetShift = timeRecorderService.selectByEmployeeId(employeeId);
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
		TimeRecord timeRecord = timeRecorderService.selectByDateAndEmployeeId(employeeId, today);
		if (Objects.equals(timeRecord, null)) {
			timeRecorderService.updateStartTimeByEmployeIdAndDate(employeeId, today);
			model.addAttribute("message", "出勤");
			return "time-recorder/execute";
		} else {
			attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
			return "redirect:/time-recorder";
		}
	}

	@PostMapping("/stamp/end")
	public String end(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes) {
		TimeRecord timeRecord = timeRecorderService.selectByDateAndEmployeeId(employeeId, today);
		if (Objects.equals(timeRecord, null)) {
			attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
			return "redirect:/time-recorder";
		}
		if (Objects.equals(timeRecord.getClockOut(), null)) {
			timeRecorderService.updateEndTimeByEmployeeIdAndDate(employeeId, today);
			model.addAttribute("message", "退勤");
			return "time-recorder/execute";
		}
		attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");
		return "redirect:/time-recorder";

	}
}
