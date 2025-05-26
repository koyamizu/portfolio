package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.State;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.helper.EntityForFullCalendarHelper;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shift")
public class ShiftManagementController {

	private final ShiftManagementService shiftManagementService;
	private final EmployeesManagementService employeesManagementService;

	@GetMapping
	public String showShiftSchedule(HttpSession session, Model model) {
		Integer thisMonth = LocalDate.now().getMonthValue();
		List<FullCalendarEntity> shifts = shiftManagementService.selectThreeMonthShiftsByTargetMonth(thisMonth);
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		String from = (String) session.getAttribute("from");
		model.addAttribute("from", from);
		model.addAttribute("shifts", shifts);
		return "shift/schedule";
	}

	@GetMapping("request")
	public String showRequestPage(Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		List<FullCalendarEntity> requests = shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
		if(!CollectionUtils.isEmpty(requests)) {
			EntityForFullCalendarHelper.setColorProperties("transparent", "transparent", requests);
			model.addAttribute("requests", requests);	
//			model.addAttribute("currentRequests",new List<FullCalendarEntity> currentRequests;
			}
		State state=(CollectionUtils.isEmpty(requests))?State.NEW:State.CONFIRM;
		model.addAttribute("state",state.toString());
		return "shift/request";
	}

//	@GetMapping("request/renew")
	@PostMapping("request/edit")
	public String editRequests(Authentication authentication
			,@RequestParam("requests") List<FullCalendarEntity> currentRequests,Model model) {
//		Integer employeeId = Integer.parseInt(authentication.getName());
//		List<FullCalendarEntity> requests = shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", currentRequests);
		model.addAttribute("state",State.EDIT.toString());
		return "shift/request";
	}

	@PostMapping("request/submit")
	public String submitRequests(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		if (selectedDatesJson.equals("[]")) {
			attributes.addFlashAttribute("errorMessage", "日付を選択してください");
		} else {

			ObjectMapper mapper = new ObjectMapper();
			List<FullCalendarForm> requests = mapper.readValue(selectedDatesJson,
					new TypeReference<List<FullCalendarForm>>() {
					});

			shiftManagementService.insertShiftRequests(requests);
			attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
		}
		return "redirect:/shift/request";
	}

	@GetMapping("edit")
	public String showEditPage(Model model) {
		//id,start(date)のみの情報が返ってくる
		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		List<FullCalendarEntity> nextMonthShifts = shiftManagementService
				.selectOneMonthShiftsByTargetMonth(nextMonth);
		if (!CollectionUtils.isEmpty(nextMonthShifts)) {
			EntityForFullCalendarHelper.setColorProperties("#FB9D00", "white", nextMonthShifts);
			model.addAllAttributes(
					Map.of(
							"nextMonthShifts", nextMonthShifts,
							"isNew", CollectionUtils.isEmpty(nextMonthShifts)));
			return "shift/edit";
		}
		List<FullCalendarEntity> requests = shiftManagementService.selectAllShiftRequests();
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", requests);
		List<Integer> submittedEmployeeIds = requests.stream().map(r -> r.getEmployeeId()).distinct().toList();
		List<Employee> allEmployees = employeesManagementService.selectAllIdAndName();
		List<Employee> notSubmits = allEmployees.stream().filter(e -> !submittedEmployeeIds.contains(e.getEmployeeId()))
				.toList();
		model.addAllAttributes(Map.of(
				"requests", requests,
				"isNew", CollectionUtils.isEmpty(nextMonthShifts),
				"allEmployees", allEmployees,
				"notSubmits", notSubmits));
		return "shift/edit";
	}

	@GetMapping("edit/renew")
	public String deleteShifts() {
		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		shiftManagementService.deleteShiftsByTargetMonth(nextMonth);
		return "forward:/shift/edit";
	}

	@PostMapping("edit/create")
	public String submitShifts(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> newShifts = mapper.readValue(selectedDatesJson,
				new TypeReference<List<FullCalendarForm>>() {
				});

		shiftManagementService.insertNextMonthShifts(newShifts);
		attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		return "redirect:/shift/edit";
	}
}
