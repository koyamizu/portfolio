package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/time_recorder")
@RequiredArgsConstructor
public class TimeRecorderController {

	private final TimeRecorderService timeRecorderService;
	LocalDate today = LocalDate.now();

	@GetMapping
	public String employees(Model model) {
		model.addAttribute("todaysEmployees", timeRecorderService.selectEmployeesByDate(today));
		return "time_recorder/top";
	}

	//	プレースホルダがシフトidになっているver.セキュリティ的にもこっちのほうがいいかも知れない
	@GetMapping("/time_recorder/{id}")
	public String start(@PathVariable Integer id,Model model,RedirectAttributes attributes) {
		List<ShiftAndTimestamp> todayEmployees=timeRecorderService.selectEmployeesByDate(today);
		ShiftAndTimestamp employee=list.stream().filter(s->s.getId()==id);
		if(employee!=null) {
			model.addAttribute("")
		}
	}

	//	GetMappingではなくPostMappingで、Viewから渡される従業員IDはbodyに格納する。その場合は、url名とかも変える。
	//	@GetMapping("/time_recorder/{id}")
	//	public String start(@PathVariable String id,Model model,RedirectAttributes attributes) {
	//		ShiftAndTimestamp shiftAndTimestamp=timeRecorderService.selectShiftAndTimestampByEmployeeIdAndDate(id, today);
	//		if(shiftAndTimestamp!=null) {
	//			model.addAttribute("")
	//		}
	//	}

}
