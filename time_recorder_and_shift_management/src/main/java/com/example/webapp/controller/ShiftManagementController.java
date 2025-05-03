package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	LocalDate year_month = LocalDate.now();

	@GetMapping
	public String showShiftTables(Model model) {
		//		List<ShiftAndTimestamp> shifts=service.selectAllShiftByYearMonth(year_month);
		//		model.addAttribute("shifts",shifts);
		return "shift/table";
	}

	@GetMapping("form")
	public String showForm(/*Model model*/) {
//		Employee employee = service.selectEmployeeById(1001);
//		model.addAttribute("employee", employee);
		return "shift/form";
	}

	@PostMapping("submit")
	public String submitShifts(@RequestParam("employee_id") Integer employeeId,
			@RequestParam("selectedDatesJson") String selectedDatesJson) throws JsonProcessingException {

		// JSON文字列を List<String> に変換
		ObjectMapper mapper = new ObjectMapper();
		List<String> dateStrs = mapper.readValue(selectedDatesJson,
				new TypeReference<List<String>>() {
				});
		
		 List<LocalDate> dates = dateStrs.stream()
			        .map(LocalDate::parse)
			        .collect(Collectors.toList());
		 
		 service.insertShiftRequests(employeeId,dates);
		 
		 //「送信しました」みたいなメッセージを表示して、「登録済み」に切り替え
		return "redirect:/shift/form";
	}
}
