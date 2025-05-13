package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftRequestForm;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/shift")
public class ShiftManagementController {

	private final ShiftManagementService service;

	@GetMapping
	public String showShiftTables(Model model,
			@ModelAttribute(value="from_time_recorder",binding=false) Boolean fromTimeRecorder) {
		model.addAttribute("from_time_recorder", Boolean.TRUE.equals(fromTimeRecorder));
		return "shift/schedule";
	}
	
	@GetMapping("/from_time_recorder")
	public String toShift(RedirectAttributes attrs) {
		attrs.addFlashAttribute("from_time_recorder", true);
		return "redirect:/shift";
	}

	@GetMapping("form")
	public String showForm(ShiftRequestForm form, Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		//id,start(date)のみの情報が返ってくる
		List<EntityForFullCalendar> requests = service.selectRequestsByEmployeeId(employeeId);
		form.setRequests(requests);
		form.setIsNew(CollectionUtils.isEmpty(requests));
		return "shift/request_form";
	}

	@GetMapping("renew")
	public String deleteRequests(Authentication authentication) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		service.deleteRequestsByEmployeeId(employeeId);
		return "forward:/shift/form";
	}

	@PostMapping("submit")
	public String submitShifts(@RequestParam("employee_id") Integer employeeId,
			@RequestParam String selectedDatesJson,
			RedirectAttributes attributes) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<String> dateStrs = mapper.readValue(selectedDatesJson,
				new TypeReference<List<String>>() {
				});

		List<LocalDate> dates = dateStrs.stream()
				.map(LocalDate::parse)
				.collect(Collectors.toList());

		service.insertShiftRequests(employeeId, dates);
		attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
		//「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/form";
	}
}
