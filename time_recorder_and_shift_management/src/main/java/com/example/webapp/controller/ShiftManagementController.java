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

	//	private final ShiftManagementService service;
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
	public String showRequestForm(Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		//id,start(date)のみの情報が返ってくる
		//↓これは別にエンティティでいい。表示用なので。
		List<EntityForFullCalendar> requests = shiftManagementService.selectRequestsByEmployeeId(employeeId);
		EntityForFullCalendarHelper.setColorProperties("transparent", "transparent", requests);
		model.addAttribute("requests", requests);
		model.addAttribute("isNew", CollectionUtils.isEmpty(requests));
		return "shift/request";
	}

	@GetMapping("request/renew")
	public String deleteRequests(Authentication authentication) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		shiftManagementService.deleteRequestsByEmployeeId(employeeId);
		return "forward:/shift/request";
	}

	@PostMapping("request/submit")
	public String submitRequests(Integer employeeId, @RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<String> dateStrs = mapper.readValue(selectedDatesJson,
				new TypeReference<List<String>>() {
				});

		List<LocalDate> dates = dateStrs.stream()
				.map(LocalDate::parse)
				.toList();

		shiftManagementService.insertShiftRequests(employeeId, dates);
		attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
		//「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/request";
	}

	@GetMapping("edit")
	public String showEditPage(/*ShiftScheduleEditForm form,*/Model model) {
		//id,start(date)のみの情報が返ってくる
		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		List<EntityForFullCalendar> shiftsOfNextMonth = shiftManagementService
				.selectOneMonthShiftsByTargetMonth(nextMonth);
		//↓これShiftRequestFormで受けないとダメ
		List<EntityForFullCalendar> requests = shiftManagementService.selectAllRequests();
		//		List<ShiftAndTimestamp> requests = shiftManagementService.selectAllRequests();
		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", requests);
		//		List<Employee> notSubmits=service.selectEmployeesNotSubmitRequests();
		List<Integer> submittedEmployeeIds = requests.stream().map(r -> r.getEmployeeId()).distinct().toList();
		List<Employee> allEmployees = employeesManagementService.selectAllEmployees();
		//		List<Employee> notSubmits=allEmployees.stream().map(e->e.getId()).forEach(ei->submittedEmployeeIds.contains(id)).toList();
		List<Employee> notSubmits = allEmployees.stream().filter(e -> !submittedEmployeeIds.contains(e.getId()))
				.toList();
		model.addAllAttributes(Map.of(
				"requests", requests,
				"shiftsOfNextMonth", shiftsOfNextMonth,
				"isNew", CollectionUtils.isEmpty(shiftsOfNextMonth),
				"allEmployees", allEmployees,
				"notSubmits", notSubmits));
		return "shift/edit";
	}

	@GetMapping("edit/renew")
	public String deleteShift() {
		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		shiftManagementService.deleteShiftScheduleByTargetMonth(nextMonth);
		return "forward:/shift/edit";
	}

	@PostMapping("edit/create")
	public String submitShifts(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<ShiftScheduleEditForm> newShifts = mapper.readValue(selectedDatesJson,
				new TypeReference<List<ShiftScheduleEditForm>>() {
				});

//		List<LocalDate> dates = newShifts.stream()
//				.map(LocalDate::parse)
//				.toList();

		shiftManagementService.insertNextMonthShifts(newShifts);
		attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		//「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/edit";
	}
}
