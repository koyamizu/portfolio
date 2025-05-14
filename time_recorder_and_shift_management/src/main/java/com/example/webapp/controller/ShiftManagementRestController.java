//どうせisNewが必要になるので、jsonは使う必要がない可能性が高い。

//package com.example.webapp.controller;
//
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.example.webapp.entity.EntityForFullCalendar;
//import com.example.webapp.service.ShiftManagementService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/api/event")
//@RequiredArgsConstructor
//public class ShiftManagementRestController {
//
//	private final ShiftManagementService service;
//
//	@GetMapping("/shifts")
//	public String getShifts() {
//		String jsonMsg = null;
//		try {
//			List<EntityForFullCalendar> shifts = service.selectAllShifts(LocalDate.now().getMonthValue());
//			// FullCalendarにエンコード済み文字列を渡す
//			ObjectMapper mapper = new ObjectMapper();
//			jsonMsg = mapper.registerModule(new JavaTimeModule()).writerWithDefaultPrettyPrinter()
//					.writeValueAsString(shifts);
//		} catch (IOException ioex) {
//			System.out.println(ioex.getMessage());
//		}
//		return jsonMsg;
//	}
//	
//	@GetMapping("/requests")
//	public String getRequests() {
//		String jsonMsg = null;
//		try {
//			List<EntityForFullCalendar> requests = service.selectAllRequests();
//			// FullCalendarにエンコード済み文字列を渡す
//			ObjectMapper mapper = new ObjectMapper();
//			jsonMsg = mapper.registerModule(new JavaTimeModule()).writerWithDefaultPrettyPrinter()
//					.writeValueAsString(requests);
//		} catch (IOException ioex) {
//			System.out.println(ioex.getMessage());
//		}
//		return jsonMsg;
//	}
//}