package com.example.webapp.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.State;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.helper.FullCalendarHelper;
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

	private final ShiftManagementService shiftManagementService;
	private final EmployeesManagementService employeesManagementService;

	@GetMapping
	public String showShiftSchedule(HttpSession session, Model model) {
		Integer thisMonth = LocalDate.now().getMonthValue();
		List<FullCalendarEntity> shifts = shiftManagementService.selectThreeMonthShiftsByTargetMonth(thisMonth);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		String from = (String) session.getAttribute("from");
		model.addAttribute("from", from);
		model.addAttribute("thisMonth", thisMonth);
		model.addAttribute("shifts", shifts);
		return "shift/schedule";
	}

	@GetMapping("request")
	public String showRequestPage(Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		List<FullCalendarEntity> requests = shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
		if (!CollectionUtils.isEmpty(requests)) {
			FullCalendarHelper.setColorProperties("transparent", "transparent", requests);
			model.addAttribute("requests", requests);
			//			model.addAttribute("currentRequests",new List<FullCalendarEntity> currentRequests;
		}
		State state = (CollectionUtils.isEmpty(requests)) ? State.NEW : State.CONFIRM;
		model.addAttribute("state", state.toString());
		return "shift/request";
	}

	//	@GetMapping("request/renew")
	@GetMapping("request/edit")
	public String editRequests(Authentication authentication, Model model) {
		Integer employeeId = Integer.parseInt(authentication.getName());
		List<FullCalendarEntity> requests = shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
		FullCalendarHelper.setColorProperties("transparent", "transparent", requests);
		model.addAttribute("requests", requests);
		model.addAttribute("state", State.EDIT.toString());
		return "shift/request";
	}

	@PostMapping("request/submit")
	public String submitRequests(@RequestParam String selectedDatesJson,
			@RequestParam State state,
			RedirectAttributes attributes,Authentication auth) throws JsonProcessingException {

		if (selectedDatesJson.equals("[]")) {
			attributes.addFlashAttribute("errorMessage", "日付を選択してください");
			return "redirect:/shift/request";
		}

		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> requests = mapper.readValue(selectedDatesJson,
				new TypeReference<List<FullCalendarForm>>() {
				});
		
		Integer employeeId=Integer.parseInt(auth.getName());
		
		if (state.equals(State.NEW)) {
			shiftManagementService.insertShiftRequests(requests);
			attributes.addFlashAttribute("message", "シフト希望の提出が完了しました");
			return "redirect:/shift/request";
		}
		
		if (state.equals(State.EDIT)) {
			List<FullCalendarEntity> oldRequestsStr=shiftManagementService.selectShiftRequestsByEmployeeId(employeeId);
			List<FullCalendarForm> oldRequests=oldRequestsStr.stream().map(o->FullCalendarHelper.convertFullCalendarForm(o)).toList();
			//additionals = Objects.equals(r.getId(), null) && oldRequestsにdateが存在しない
			List<FullCalendarForm> additionals = requests.stream()
//					新しく追加した希望日
					.filter(r ->Objects.equals(r.getId(),null)
//							かつ、すでに登録してある日付と重複していない
							&& oldRequests.stream().noneMatch(o->r.getStart().isEqual(o.getStart()))
							).toList();			
			if (!CollectionUtils.isEmpty(additionals)) {
				shiftManagementService.insertAdditionalRequest(additionals);
			}
			//newRequestsに存在しない日付を削除
			shiftManagementService.deleteByEmployeeId(requests, employeeId);
			attributes.addFlashAttribute("message", "シフト希望を更新しました");
		}
		return "redirect:/shift/request";
	}

	@GetMapping("create")
	public String showCreatePage(Model model) {
		//id,start(date)のみの情報が返ってくる
		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		//		↓クエリにOrder Byを足す
		List<FullCalendarEntity> nextMonthShifts = shiftManagementService
				.selectOneMonthShiftsByTargetMonth(nextMonth);
		if (!CollectionUtils.isEmpty(nextMonthShifts)) {
			FullCalendarHelper.setColorProperties("#FB9D00", "white", nextMonthShifts);
			//			むしろ行数増えているので二行で
			model.addAllAttributes(
					Map.of(
							"nextMonthShifts", nextMonthShifts,
							"state", State.CONFIRM.toString(),
							"month", nextMonth));
			return "shift/create";
		}
		List<FullCalendarEntity> requests = shiftManagementService.selectAllShiftRequests();
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", requests);
		//		submittedはここでしか使っていないので、未提出者はSQLで取得する
		//		List<Integer> submittedEmployeeIds = requests.stream().map(r -> r.getEmployeeId()).distinct().toList();
		List<Employee> allEmployees = employeesManagementService.selectAllIdAndName();
		List<Employee> notSubmits = shiftManagementService.selectEmployeesNotSubmitRequests();
		//				allEmployees.stream().filter(e -> !submittedEmployeeIds.contains(e.getEmployeeId()))
		//				.toList();
		model.addAllAttributes(Map.of(
				"requests", requests,
				"state", State.NEW.toString(),
				"allEmployees", allEmployees,
				"notSubmits", notSubmits,
				"month", nextMonth));
		return "shift/create";
	}

	@GetMapping("create/edit/{month}")
	public String deleteShifts(@PathVariable Integer month, Model model) {
		List<FullCalendarEntity> shifts = shiftManagementService
				.selectOneMonthShiftsByTargetMonth(month);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		List<Employee> allEmployees = employeesManagementService.selectAllIdAndName();
		//initializeCalendarの引数が「requests」なので、requestsとして格納
		model.addAttribute("requests", shifts);
		model.addAttribute("month", month);
		model.addAttribute("state", State.EDIT.toString());
		model.addAttribute("allEmployees", allEmployees);
		//		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		//		List<FullCalendarEntity> nextMonthShifts = shiftManagementService
		//				.selectOneMonthShiftsByTargetMonth(nextMonth);
		//		EntityForFullCalendarHelper.setColorProperties("#02e09a", "#006666", nextMonthShifts);
		//		List<Employee> allEmployees = employeesManagementService.selectAllIdAndName();
		//		//initializeCalendarの引数が「requests」なので、requestsとして格納
		//		model.addAttribute("requests", nextMonthShifts);
		//		model.addAttribute("state", State.EDIT.toString());
		//		model.addAttribute("allEmployees",allEmployees);
		return "shift/create";
	}

	@PostMapping("create/execute")
	public String submitShifts(@RequestParam String selectedDatesJson,
			RedirectAttributes attributes, @RequestParam State state, @RequestParam Integer month)
			throws JsonProcessingException {

		//		Integer nextMonth = LocalDate.now().getMonthValue() + 1;
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> newShifts = mapper.readValue(selectedDatesJson,
				new TypeReference<List<FullCalendarForm>>() {
				});

		//		shiftManagementService.insertNextMonthShifts(newShifts);
		//		attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		//		return "redirect:/shift/edit";

		if (state.equals(State.NEW)) {
			shiftManagementService.insertNextMonthShifts(newShifts);
			attributes.addFlashAttribute("message", "シフトの作成が完了しました");
		}
		if (state.equals(State.EDIT)) {
			List<FullCalendarForm> additionals = newShifts.stream().filter(r -> Objects.equals(r.getId(), null))
					.toList();
			//変更なしで「更新」を押したときは迂回する。
			if (!CollectionUtils.isEmpty(additionals)) {
				//				↓xmlファイルの編集
				shiftManagementService.insertAdditionalShift(additionals);
			}
			shiftManagementService.deleteByMonth(newShifts, month);
			attributes.addFlashAttribute("message", "シフトを更新しました");
		}
		String path = (month == LocalDate.now().getMonthValue()) ? "/shift" : "/shift/create";
		return "redirect:" + path;
	}
}
