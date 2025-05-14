package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;

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
import com.example.webapp.form.ShiftRequestForm;
import com.example.webapp.form.ShiftScheduleEditForm;
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

	private final ShiftManagementService service;

	@GetMapping
	public String showShiftSchedule(HttpSession session,Model model) {
		Integer thisMonth=LocalDate.now().getMonthValue();
		List<EntityForFullCalendar> shifts = service.selectThreeMonthShiftsByTargetMonth(thisMonth);
		String from=(String)session.getAttribute("from");
		model.addAttribute("from",from);
		model.addAttribute("shifts",shifts);
		return "shift/schedule";
	}

	@GetMapping("request")
	public String showRequestForm(ShiftRequestForm form, Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		//id,start(date)のみの情報が返ってくる
		List<EntityForFullCalendar> requests = service.selectRequestsByEmployeeId(employeeId);
		form.setRequests(requests);
		form.setIsNew(CollectionUtils.isEmpty(requests));
		return "shift/request";
	}

	@GetMapping("request/renew")
	public String deleteRequests(Authentication authentication) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		service.deleteRequestsByEmployeeId(employeeId);
		return "forward:/shift/request";
	}

	@PostMapping("request/submit")
	public String submitRequests(@RequestParam("employee_id") Integer employeeId,
			@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<String> dateStrs = mapper.readValue(selectedDatesJson,
				new TypeReference<List<String>>() {
				});

		List<LocalDate> dates = dateStrs.stream()
				.map(LocalDate::parse)
				.toList();

		service.insertShiftRequests(employeeId, dates);
		attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
		//「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/request";
	}
	
	@GetMapping("edit")
	public String showeditPage(ShiftScheduleEditForm form,Model model) {
		//id,start(date)のみの情報が返ってくる
		Integer nextMonth=LocalDate.now().getMonthValue()+1;
		List<EntityForFullCalendar> shiftsOfNextMonth=service.selectOneMonthShiftsByTargetMonth(nextMonth);
		List<EntityForFullCalendar> requests = service.selectAllRequests();
		List<Employee> notSubmits=service.selectEmployeesNotSubmitRequests();
		form.setRequests(requests);
		form.setShiftsOfNextMonth(shiftsOfNextMonth);
		form.setIsNew(CollectionUtils.isEmpty(shiftsOfNextMonth));
		form.setNotSubmit(notSubmits);
		return "shift/edit";
	}

	@GetMapping("edit/renew")
	public String deleteShift() {
		Integer nextMonth=LocalDate.now().getMonthValue()+1;
		service.deleteShiftScheduleByTargetMonth(nextMonth);
		return "forward:/shift/edit";
	}

	@PostMapping("edit/create")
	public String submitShifts(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<String> dateStrs = mapper.readValue(selectedDatesJson,
				new TypeReference<List<String>>() {
				});

		List<LocalDate> dates = dateStrs.stream()
				.map(LocalDate::parse)
				.toList();

		service.insertShiftsOfNextMonth(dates);
		attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		//「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/edit";
	}
}
