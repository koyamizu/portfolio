package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.form.EmployeeForm;
import com.example.webapp.form.PasswordForm;
import com.example.webapp.service.PasswordService;
import com.example.webapp.utility.PasswordGenerator;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/password")
public class PasswordController {

	private final PasswordService passwordService;

	@GetMapping("reset/{employee-id}")
	public String showPasswordResetForm(@PathVariable("employee-id") Integer employeeId, PasswordForm passwordForm) {
		passwordForm.setEmployeeId(employeeId);
		passwordForm.setIsNew(false);
		return "password/form";
	}

	@PostMapping("create")
	public String showPasswordCreationForm(@Validated EmployeeForm employeeForm,
			BindingResult bindingResult, PasswordForm passwordForm, HttpSession session) {
		if (bindingResult.hasErrors()) {
			employeeForm.setIsNew(true);
			return "employees/form";
		}
		session.setAttribute("newEmployee", employeeForm);
		passwordForm.setIsNew(true);
		return "password/form";
	}

	@PostMapping("update")
	public String updatePassword(@Validated PasswordForm passwordForm, BindingResult bindingResult,
			RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return "password/form";
		}
		String newBcryptPassword = PasswordGenerator.generatePassword(passwordForm.getNewRawPassword());
		passwordService.updatePassword(passwordForm.getEmployeeId(), newBcryptPassword);
		attributes.addFlashAttribute("message", "従業員ID:" + passwordForm.getEmployeeId() + "のパスワードが更新されました");
		return "redirect:/employees";
	}
}
