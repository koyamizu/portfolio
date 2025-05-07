package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.form.PasswordForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.service.PasswordService;
import com.example.webapp.utility.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordController {

	private final PasswordService passwordService;
	private final EmployeesManagementService employeeManagementService;

	@GetMapping("reissue/{id}")
	public String showPasswordReissueForm(@PathVariable Integer id,Model model) {
		Employee employee=employeeManagementService.selectEmployeeById(id);
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(employee);
		model.addAttribute("employeeForm",form);
		return "password/form";
	}
	
	@PostMapping("create/")
	public String showPasswordCreationForm(EmployeeForm form, Model model) {
		model.addAttribute("employeeForm",form);
		return "password/form";
	}
	
	@GetMapping("execute")
	public String reissue(PasswordForm form, RedirectAttributes attributes) {
		String newBcryptPassword = PasswordGenerator.generatePassword(form.getNewRawPassword());
		try {
			passwordService.updatePasswordByEmployeeId(form.getEmployeeId(), newBcryptPassword);
			attributes.addFlashAttribute("message", "従業員ID:"+form.getEmployeeId()+"のパスワードが更新されました");
			return "redirect:/employees";
		} catch (Exception e) {
			attributes.addFlashAttribute("errorMessage", "パスワードの更新処理に失敗しました");
			return "redirect:/employees";
		}
	}
}
