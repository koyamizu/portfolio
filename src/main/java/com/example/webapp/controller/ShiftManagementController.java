package com.example.webapp.controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.FullCalendarDisplay;
import com.example.webapp.entity.ShiftCreateContainer;
import com.example.webapp.entity.ShiftEditContainer;
import com.example.webapp.entity.ShiftRequestDeadLine;
import com.example.webapp.entity.State;
import com.example.webapp.exception.DuplicateShiftException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.helper.FullCalendarHelper;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shift")
public class ShiftManagementController {

	private final ShiftManagementService shiftManagementService;

	@GetMapping
	public String showShiftSchedule(HttpSession session, Model model) {
		Integer thisMonth = LocalDate.now().getMonthValue();
		List<FullCalendarDisplay> shifts = shiftManagementService.getThreeMonthShifts(thisMonth);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		String from = (String) session.getAttribute("from");
		model.addAttribute("from", from);
		model.addAttribute("thisMonth", thisMonth);
		model.addAttribute("shifts", shifts);
		return "shift/schedule";
	}

	@GetMapping("request")
	public String showRequestPage(Authentication authentication, Model model,RedirectAttributes attributes) {
		ShiftRequestDeadLine deadLine=new ShiftRequestDeadLine(LocalDate.now());
		model.addAttribute("deadLine", deadLine.toString());
		
		Integer employeeId = Integer.parseInt(authentication.getName());
		List<FullCalendarDisplay> requests = shiftManagementService.getPersonalShiftRequests(employeeId);
		
		State state = CollectionUtils.isEmpty(requests) ? State.NEW : State.CONFIRM;
		
		if (state.equals(State.CONFIRM)) {
			FullCalendarHelper.setColorProperties("transparent", "transparent", requests);
			model.addAttribute("requests", requests);
			model.addAttribute("state", state.toString());
			return "shift/request";
		}
		
		if(deadLine.isOverDeadLine(LocalDate.now())) {
			attributes.addFlashAttribute("errorMessage", "シフトの提出期限を超えています。責任者に直接希望日を伝えてください。");
			return "redirect:/shift";
		}
		
		if (state.equals(State.NEW)) {
			model.addAttribute("state", state.toString());
		}
		return "shift/request";
	}

	@GetMapping("request/edit")
	public String editRequests(Authentication authentication, Model model,RedirectAttributes attributes) {
		ShiftRequestDeadLine deadLine=new ShiftRequestDeadLine(LocalDate.now());
		if(deadLine.isOverDeadLine(LocalDate.now())) {
			attributes.addFlashAttribute("errorMessage", "シフトの編集可能な日付を超えています。変更があれば、責任者に直接伝えてください。");
			return "redirect:/shift";
		}
		Integer employeeId = Integer.parseInt(authentication.getName());
		List<FullCalendarDisplay> requests = shiftManagementService.getPersonalShiftRequests(employeeId);
		FullCalendarHelper.setColorProperties("transparent", "transparent", requests);
		model.addAttribute("requests", requests);
		model.addAttribute("state", State.EDIT.toString());
		return "shift/request";
	}

	//↓ここから
	@PostMapping("request/submit")
	public String submitRequests(@RequestParam String selectedDatesJson,
			@RequestParam State state,
			RedirectAttributes attributes, Authentication auth)
			throws JsonProcessingException, DuplicateShiftException {

		if (selectedDatesJson.equals("[]")) {
			attributes.addFlashAttribute("errorMessage", "日付を選択してください");
			return "redirect:/shift/request";
		}

		Integer employeeId = Integer.parseInt(auth.getName());

		if (state.equals(State.NEW)) {
			shiftManagementService.registerShiftRequests(selectedDatesJson, employeeId);
			attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
			return "redirect:/shift/request";
		}

		if (state.equals(State.EDIT)) {
			shiftManagementService.updateShiftRequests(selectedDatesJson, employeeId);
			attributes.addFlashAttribute("message", "シフト希望を更新しました");
		}
		return "redirect:/shift/request";
	}

	@GetMapping("create")
	public String showCreatePage(Model model) {
		ShiftRequestDeadLine deadLine=new ShiftRequestDeadLine(LocalDate.now());
		model.addAttribute("deadLine", deadLine.toString());
		
		Integer nextMonth = LocalDate.now().plus(1, ChronoUnit.MONTHS).getMonthValue();
		model.addAttribute("month", nextMonth);
		//id,start(date)のみの情報が返ってくる
		List<FullCalendarDisplay> nextMonthShifts = shiftManagementService
				.getOneMonthShifts(nextMonth);
		
		State state = CollectionUtils.isEmpty(nextMonthShifts) ? State.NEW : State.CONFIRM;
		model.addAttribute("state", state.toString());

		if (state.equals(State.CONFIRM)) {
			FullCalendarHelper.setColorProperties("#FB9D00", "white", nextMonthShifts);
			model.addAttribute("nextMonthShifts", nextMonthShifts);
			return "shift/create";
		}

		if (state.equals(State.NEW)) {
			ShiftCreateContainer container = shiftManagementService.initializeShiftCreateContainerFields();
			model.addAllAttributes(Map.of(
					"requests", container.getRequests(),
					"allEmployees", container.getAllEmployees(),
					"notSubmits", container.getNotSubmits()));
		}
		return "shift/create";
	}

	@GetMapping("create/edit/{month}")
	public String deleteShifts(@PathVariable Integer month, Model model) {
		ShiftEditContainer container = shiftManagementService.initializeShiftEditContainerFields(month);
		//		確定したシフトの編集なので、requestというよりshiftなのだが、
		//		initializeCalendarの引数が「requests」なので、requestsとして格納
		model.addAttribute("requests", container.getShifts());
		model.addAttribute("month", month);
		model.addAttribute("state", State.EDIT.toString());
		model.addAttribute("allEmployees", container.getAllEmployees());
		return "shift/create";
	}

	@PostMapping("create/submit")
	public String submitShifts(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes, @RequestParam State state, @RequestParam Integer month
			,HttpSession session)
			throws JsonProcessingException, InvalidEditException {

		if (state.equals(State.EDIT)) {
			shiftManagementService.updateShiftSchedules(selectedDatesJson, month);
			attributes.addFlashAttribute("message", "シフトを更新しました");
		}
		
		ShiftRequestDeadLine deadLine=new ShiftRequestDeadLine(LocalDate.now());
		
		if(deadLine.isJustOrUnderDeadLine(LocalDate.now())) {
			attributes.addFlashAttribute("errorMessage", deadLine.toString()+"まではシフトの作成ができません");
			return "redirect:/shift";
		}
		
		if (state.equals(State.NEW)) {
			shiftManagementService.createNextMonthShifts(selectedDatesJson);
			attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		}
		String path = (month == LocalDate.now().getMonthValue()) ? "/shift" : "/shift/create";
		return "redirect:" + path;
	}

	@ExceptionHandler({ DuplicateShiftException.class })
	public String redirectToShiftRequestPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/shift/request";
	}

	@ExceptionHandler({ InvalidEditException.class })
	public String redirectToShiftCreatePage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/shift/create/edit/" + LocalDate.now().getMonthValue();
	}
}
