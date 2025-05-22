package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;
import com.example.webapp.helper.EntityForFullCalendarHelper;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
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
		List<EntityForFullCalendar> shifts = shiftManagementService.selectThreeMonthShiftsByTargetMonth(thisMonth);
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		String from = (String) session.getAttribute("from");
		model.addAttribute("from", from);
		model.addAttribute("shifts", shifts);
		return "shift/schedule";
	}

	@GetMapping("request")
	public String showRequestPage(Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		//id,start(date)のみの情報が返ってくる
		List<EntityForFullCalendar> requests = shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
		EntityForFullCalendarHelper.setColorProperties("transparent", "transparent", requests);
		model.addAttribute("requests", requests);
		model.addAttribute("isNew", CollectionUtils.isEmpty(requests));
		return "shift/request";
	}

	@GetMapping("request/renew")
	public String deleteRequests(Authentication authentication) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		shiftManagementService.deleteShiftRequestsByEmployeeId(employeeId);
		return "forward:/shift/request";
	}

	@PostMapping("request/submit")
	public String submitRequests(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		if (selectedDatesJson.equals("[]")) {
			attributes.addFlashAttribute("errorMessage", "日付を選択してください");
		} else {

			ObjectMapper mapper = new ObjectMapper();
			List<ShiftScheduleEditForm> requests = mapper.readValue(selectedDatesJson,
					new TypeReference<List<ShiftScheduleEditForm>>() {
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
		List<EntityForFullCalendar> nextMonthShifts = shiftManagementService
				.selectOneMonthShiftsByTargetMonth(nextMonth);
		if(!CollectionUtils.isEmpty(nextMonthShifts)) {
			EntityForFullCalendarHelper.setColorProperties("#FB9D00", "white", nextMonthShifts);
			model.addAllAttributes(Map.of(
//					"requests", null,
					"nextMonthShifts", nextMonthShifts,
					"isNew", CollectionUtils.isEmpty(nextMonthShifts)
//					,"allEmployees", null,
//					"notSubmits", null
					));
			return "shift/edit";
		}
		List<EntityForFullCalendar> requests = shiftManagementService.selectAllShiftRequests();
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", requests);
		List<Integer> submittedEmployeeIds = requests.stream().map(r -> r.getEmployeeId()).distinct().toList();
		//↓住所とかもselectされちゃってるので、employee_id,nameだけを取り出すマッパーメソッドを作る
		List<Employee> allEmployees = employeesManagementService.selectAllEmployees();
		List<Employee> notSubmits = allEmployees.stream().filter(e -> !submittedEmployeeIds.contains(e.getEmployeeId()))
				.toList();
		model.addAllAttributes(Map.of(
				"requests", requests,
//				"nextMonthShifts", nextMonthShifts,
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
		List<ShiftScheduleEditForm> newShifts = mapper.readValue(selectedDatesJson,
				new TypeReference<List<ShiftScheduleEditForm>>() {
				});

		shiftManagementService.insertNextMonthShifts(newShifts);
		attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		return "redirect:/shift/edit";
	}
}
