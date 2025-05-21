package com.example.webapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String showEmployeesList(Model model) {
		List<Employee> employees=service.selectAllEmployees();
		model.addAttribute("employees", employees);
		return "employees/list";
	}

	@GetMapping("register")
	public String registerNewEmployee(EmployeeForm form) {
		form.setIsNew(true);
		return "employees/form";
	}

	@GetMapping("edit/{id}")
	public String editEmployee(@PathVariable Integer id, Model model, RedirectAttributes attributes) {
		Employee target = service.selectEmployeeById(id);
		if (target != null) {
			EmployeeForm form = EmployeeHelper.convertEmployeeForm(target);
			model.addAttribute("employeeForm", form);
			return "employees/form";
		} else {
			attributes.addFlashAttribute("errorMessage", "そのIDをもつ従業員データは存在しません");
			return "redirect:/employees";
		}
	}

	@PostMapping("update")
	public String updateEmployee(@Validated EmployeeForm form, BindingResult bindingResult,RedirectAttributes attributes) {
		if(bindingResult.hasErrors()) {
			return "employees/form";
		}
		Employee employee = EmployeeHelper.convertEmployee(form);
		service.updateEmployee(employee);
		attributes.addFlashAttribute("message", "従業員情報が更新されました");
		return "redirect:/employees";
	}

	@GetMapping("delete/{id}")
	public String deleteEmployee(@PathVariable Integer id, RedirectAttributes attributes) {
		var target = service.selectEmployeeById(id);
		if (target != null) {
			try {
				service.deleteEmployeeById(id);
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
	
	@GetMapping("detail/{id}")
	public String showEmployeeDetail(@PathVariable Integer id,Model model) {
		Employee employee=service.selectEmployeeById(id);
		model.addAttribute("employee",employee);
		return "employees/detail";
	}
}
