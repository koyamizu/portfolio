package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.service.EmployeesManagementService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesManagementController {

	private final EmployeesManagementService service;

	@GetMapping
	public String list(Model model) {
		model.addAttribute("employees", service.selectAllEmployees());
		return "employees/list";
	}

	@GetMapping("/register")
	public String register(@ModelAttribute EmployeeForm form) {
		form.setIsNew(true);
		return "employees/form";
	}

	//saveとupdateはまとめてsaveとかにして、条件分岐で分けてもいいかもしれない。
	@PostMapping("/save")
	public String save(EmployeeForm form, RedirectAttributes attributes) {
		var employee = EmployeeHelper.convertEmployee(form);
		service.insertEmployee(employee);
		attributes.addFlashAttribute("message", "新規従業員が登録されました");
		return "redirect:/employees";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
		var target = service.selectEmployeeById(id);
		if (target != null) {
			EmployeeForm form = EmployeeHelper.convertEmployeeForm(target);
			model.addAttribute("employeeForm", form);
			return "employees/form";
		} else {
			attributes.addFlashAttribute("errorMessage", "そのIDをもつ従業員データは存在しません");
			return "redirect:/employees";
		}
	}

	@PostMapping("/update")
	public String update(EmployeeForm form, RedirectAttributes attributes) {
		var employee = EmployeeHelper.convertEmployee(form);
		service.updateEmployee(employee);
		attributes.addFlashAttribute("message", "従業員情報が更新されました");
		return "redirect:/employees";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
		var target = service.selectEmployeeById(id);
		if (target != null) {
			try {
				service.deleteEmployee(id);
				attributes.addFlashAttribute("message",
						"従業員ID:" + target.getId() + " " + target.getName() + " さんの従業員情報が削除されました");
			} catch (Exception e) {
				attributes.addFlashAttribute("errorMessage", "そのIDをもつ従業員はシフトに登録されているので削除できません。");
			}
			return "redirect:/employees";
		} else {
			attributes.addFlashAttribute("errorMessage", "そのIDをもつ従業員データは存在しません");
			return "redirect:/employees";
		}
	}
	
	@GetMapping("/detail/{id}")
	public String showDetail(@PathVariable Integer id,Model model) {
		Employee employee=service.selectEmployeeById(id);
		model.addAttribute("employee",employee);
		return "employees/detail";
	}
}
