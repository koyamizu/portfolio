package com.example.webapp.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public String timeRecorder(Model model) {
		model.addAttribute("todaysEmployees", timeRecorderService.selectEmployeesByDate(today));
		return "time_recorder/top";
	}

	//	プレースホルダがシフトidになっているver.セキュリティ的にもこっちのほうがいいかも知れない
//	@GetMapping("/time_recorder/{id}")
//	public String start(@PathVariable Integer id,Model model,RedirectAttributes attributes) {
//		List<ShiftAndTimestamp> todayEmployees=timeRecorderService.selectEmployeesByDate(today);
//		var employee=todayEmployees.stream().filter(s->s.getId()==id).findFirst().orElse(null);
//		if(employee!=null) {
//			model.addAttribute("employee",employee);
//			return "time_recorder/stamp";
//		}else {
//			//そのIDをもつ従業員は本日出勤予定ではありません、とかでもいいかも
//			attributes.addFlashAttribute("errorMessage","シフトデータが存在しません");
//			return "redirect:/top";
//		}

	//	GetMappingではなくPostMappingで、Viewから渡される従業員IDはbodyに格納する。その場合は、url名とかも変える。
		@PostMapping("/stamp")
		public String stamp(@RequestParam String employee_id,Model model,RedirectAttributes attributes) {
			ShiftAndTimestamp shiftAndTimestamp=timeRecorderService.selectShiftAndTimestampByEmployeeIdAndDate(employee_id, today);
			if(shiftAndTimestamp!=null) {
				model.addAttribute("employee",shiftAndTimestamp.getEmployee());
				model.addAttribute("today",shiftAndTimestamp.getDate());
				model.addAttribute("shift_id",shiftAndTimestamp.getId());
				return "time_recorder/stamp";
			}else {
				//そのIDをもつ従業員は本日出勤予定ではありません、とかでもいいかも
				attributes.addFlashAttribute("errorMessage","シフトデータが存在しません");
				return "redirect:/time_recorder";
			}
		}
		
		@PostMapping("/stamp/start")
		public String start(@RequestParam Integer shift_id,Model model,RedirectAttributes attributes) {
			ShiftAndTimestamp shiftAndTimestamp=timeRecorderService.selectShiftAndTimestampByShiftId(shift_id);
			if(shiftAndTimestamp.getStart()==null) {
				timeRecorderService.start(shift_id);
				model.addAttribute("message", "出勤");
				return "time_recorder/execute";
			}else {
				attributes.addFlashAttribute("errorMessage", "すでに出勤済みです");
				return "redirect:/time_recorder";
			}
		}
		
		@PostMapping(value="/stamp/end",params="shift_id")
		public String end(@RequestParam Integer shift_id,Model model,RedirectAttributes attributes) {
			ShiftAndTimestamp shiftAndTimestamp=timeRecorderService.selectShiftAndTimestampByShiftId(shift_id);
			if(shiftAndTimestamp.getStart()==null) {
				attributes.addFlashAttribute("errorMessage", "「出勤」より先に「退勤」は押せません");
				return "redirect:/time_recorder";
			}
			if(shiftAndTimestamp.getEnd()==null) {
				timeRecorderService.end(shift_id);
				model.addAttribute("message","退勤");
				return "time_recorder/execute";
			}else {
				attributes.addFlashAttribute("errorMessage", "すでに退勤済みです");
				return "redirect:/time_recorder";
			}
		}
}
