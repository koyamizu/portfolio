package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shift")
@RequiredArgsConstructor
public class ShiftController {
	
	private final ShiftService service;
	
	@GetMapping
	public String showShiftTable(Model model) {
		
	}
}
