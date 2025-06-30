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
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.helper.Caster;
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
	public String showTimeRecorder(Model model, HttpSession session) throws NoDataException {

		List<AbsenceApplication> todayAbsences = Caster
				.castToAbsenceApplicationList(session.getAttribute("todayAbsences"));
		List<ShiftSchedule> todayMembersWithClockTime = timeRecorderService.getEmployeeWithClockTime(today);

		//		当日欠勤者がセッションに保存されていないとき(ホームからタイムレコーダーに推移してきたとき)
		if (Objects.equals(null, todayAbsences)) {
			todayAbsences = absenceApplicationService.getTodayApplications();
			session.setAttribute("todayAbsences", todayAbsences);
		}

		model.addAttribute("todayAbsences", todayAbsences);
		model.addAttribute("todayMembersWithClockTime", todayMembersWithClockTime);
		return "time-recorder/top";
	}

	@PostMapping("/record")
	public String showRecordPage(@RequestParam("employee-id") Integer employeeId, Model model,
			RedirectAttributes attributes) throws NoDataException {
		List<ShiftSchedule> todayMembersWithClockTime = timeRecorderService.getEmployeeWithClockTime(today);
		Employee targetEmployee = timeRecorderService.getEmployeeToClock(todayMembersWithClockTime, employeeId);
		model.addAttribute("employee", targetEmployee);
		model.addAttribute("today", today);
		return "time-recorder/record";
	}

	@PostMapping("/clock-in")
	public String start(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes)
			throws DuplicateClockException {
		timeRecorderService.clockIn(employeeId);
		model.addAttribute("message", "出勤");
		return "time-recorder/execute";
	}

	@PostMapping("/clock-out")
	public String end(@RequestParam("employee-id") Integer employeeId, Model model, RedirectAttributes attributes)
			throws DuplicateClockException, InvalidClockException {
		timeRecorderService.clockOut(employeeId);
		model.addAttribute("message", "退勤");
		return "time-recorder/execute";
	}

	@ExceptionHandler({ InvalidClockException.class, NoDataException.class, InvalidEmployeeIdException.class,
			DuplicateClockException.class, InvalidClockException.class })
	public String redirectToTimeRecorderPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/time-recorder";
	}
}
