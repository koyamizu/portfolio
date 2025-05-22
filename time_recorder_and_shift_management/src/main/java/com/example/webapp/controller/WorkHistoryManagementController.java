package com.example.webapp.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;
import com.example.webapp.service.WorkHistoryManagementService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/work-history")
public class WorkHistoryManagementController {

	private final WorkHistoryManagementService service;

	//このメソッドだけadminページから来たverとuserページから来たverで分けるかもしれない
	@GetMapping("{target-month}")
	public String showWorkHistories(@PathVariable("target-month") Integer targetMonth,
			Authentication auth, Model model, HttpSession session) {
		List<ShiftAndTimeRecord> histories;
		List<Employee> employees;
		String from = (String) session.getAttribute("from");
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin && from.equals("admin")) {
			histories = service.selectAllWorkHistoriesToDateByMonth(targetMonth);
			//従業員IDと名前だけを返すマッパーメソッドを作る？　シフト編集ページのドラッグ＆ドロップ部分にも使えそう
			employees = histories.stream().map(ShiftAndTimeRecord::getEmployee).distinct().toList();
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			session.setAttribute("fromPage",targetMonth);
		} else {
			Integer employeeId = Integer.parseInt(auth.getName());
			histories = service.selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId, targetMonth);
		}
		model.addAttribute("histories", histories);
		model.addAttribute("from", from);
		return "work-history/history";
	}

	@GetMapping("{target-month}/{employee-id}")
	public String showPersonalWorkHistories(@PathVariable("target-month") Integer targetMonth, @PathVariable("employee-id") Integer employeeId,
			Authentication auth, Model model, HttpSession session) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
			List<ShiftAndTimeRecord> personalHistories = service.selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId,
					targetMonth);
			List<Employee> employees = service.selectWorkedEmployeesByMonth(targetMonth);
			String from = (String) session.getAttribute("from");
			model.addAttribute("from", from);
			model.addAttribute("employees", employees);
			model.addAttribute("targetMonth", targetMonth);
			model.addAttribute("histories", personalHistories);
			session.setAttribute("fromPage", targetMonth+"/"+employeeId);
		}
		return "work-history/history";
	}

	@GetMapping("edit/{shift-id}")
	public String showPersonalWorkHistory(@PathVariable("shift-id") Integer shiftId,Authentication auth, Model model
			,ShiftAndTimeRecordForm form/*,HttpSession session*/) {
		if (auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()))) {
//			var sessionName=session.getAttribute("fromPage");
//			String fromPage=(sessionName instanceof Integer)? sessionName.toString():(String)sessionName;
			ShiftAndTimeRecord targetHistoriy = service.selectWorkHistoryByShiftId(shiftId);
			model.addAttribute("history", targetHistoriy);
//			model.addAttribute("fromPage",fromPage);
		}
		return "work-history/edit";
	}
	
	@PostMapping("update")
	public String updateWorkHistory(ShiftAndTimeRecordForm updatedHistory,RedirectAttributes attribute
			,HttpSession session) {
		service.updateWorkHistory(updatedHistory);
		var sessionName=session.getAttribute("fromPage");
		String fromPage=(sessionName instanceof Integer)? sessionName.toString():(String)sessionName;
		attribute.addFlashAttribute("message","更新しました");
		return "redirect:/work-history/"+fromPage;
	}
}
