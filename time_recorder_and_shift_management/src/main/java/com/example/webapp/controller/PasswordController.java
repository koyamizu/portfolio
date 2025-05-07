package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	public String showPasswordReissueForm(@PathVariable Integer id, PasswordForm passwordForm) {
		passwordForm.setEmployeeId(id);
		passwordForm.setIsNew(false);
		return "password/form";
	}

	@PostMapping("create")
	public String showPasswordCreationForm(@ModelAttribute("employeeForm") EmployeeForm employeeForm,
			@ModelAttribute("passwordForm") PasswordForm passwordForm,
			BindingResult bindingResult) {
		employeeForm.setPassword(employeeForm.getBirth());
		if (bindingResult.hasErrors()) {
			return "employees/form";
		}
		var employee = EmployeeHelper.convertEmployee(employeeForm);
		employeeManagementService.insertEmployee(employee);
		passwordForm.setIsNew(true);
		return "password/form";
	}

	@PostMapping("execute")
	public String reissue(@Validated PasswordForm passwordForm, BindingResult bindingResult,
			RedirectAttributes attributes) {
		if (bindingResult.hasErrors()) {
			return "password/form";
		}
		String newBcryptPassword = PasswordGenerator.generatePassword(passwordForm.getNewRawPassword());
		try {
			passwordService.updatePasswordByEmployeeId(passwordForm.getEmployeeId(), newBcryptPassword);
			if (passwordForm.getIsNew()) {
				attributes.addFlashAttribute("message", "新規従業員の登録が完了しました");
			} else {
				attributes.addFlashAttribute("message", "従業員ID:" + passwordForm.getEmployeeId() + "のパスワードが更新されました");
			}
			return "redirect:/employees";
		} catch (Exception e) {
			if (passwordForm.getIsNew()) {
				attributes.addFlashAttribute("errorMessage", "パスワードの登録に失敗しました。西暦を含めたハイフン区切りの生年月日が初期パスワードとして登録されています。");
			} else {
				attributes.addFlashAttribute("errorMessage", "パスワードの更新処理に失敗しました");
			}
			return "redirect:/employees";
		}
	}
}
