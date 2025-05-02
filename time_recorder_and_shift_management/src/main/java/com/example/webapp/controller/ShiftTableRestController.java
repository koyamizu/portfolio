package com.example.webapp.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/event")
@RequiredArgsConstructor
public class ShiftTableRestController {
	
	private final ShiftManagementService service;
	LocalDate targetMonthAnyDate=LocalDate.of(2025, 4, 1);
	
	@GetMapping("/all")
	public String getShifts() {
		String jsonMsg = null;
		try {
			List<EntityForFullCalendar> shifts =service.selectAllShiftByYearMonth(targetMonthAnyDate);
			// FullCalendarにエンコード済み文字列を渡す
			ObjectMapper mapper = new ObjectMapper();
			jsonMsg =  mapper.registerModule(new JavaTimeModule()).writerWithDefaultPrettyPrinter().writeValueAsString(shifts);
		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
		return jsonMsg;
}
}