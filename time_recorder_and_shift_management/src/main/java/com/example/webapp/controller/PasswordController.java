package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
	private final EmployeesManagementService employeesManagementService;

	@GetMapping("reset/{employee-id}")
	public String showPasswordResetForm(@PathVariable("employee-id") Integer employeeId, PasswordForm passwordForm) {
		passwordForm.setEmployeeId(employeeId);
		passwordForm.setIsNew(false);
		return "password/form";
	}

	@PostMapping("create")
	public String showPasswordCreationForm(@Validated EmployeeForm employeeForm,
			BindingResult bindingResult,PasswordForm passwordForm) {
//		employeesテーブルのpasswordカラムはnot nullなので、初期パスワードを設定。誕生日を使用。
		employeeForm.setPassword(employeeForm.getBirth());
		if (bindingResult.hasErrors()) {
			employeeForm.setIsNew(true);
			return "employees/form";
		}
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		employeesManagementService.insertEmployee(employee);
//		↓ここで例外放出（class com.example.webapp.entity.Employee cannot be cast to class java.lang.Integer (com.example.webapp.entity.Employee is in unnamed module of loader org.springframework.boot.devtools.restart.classloader.RestartClassLoader @398cf60d; java.lang.Integer is in module java.base of loader 'bootstrap')
//		java.lang.ClassCastException: class com.example.webapp.entity.Employee cannot be cast to class java.lang.Integer (com.example.webapp.entity.Employee is in unnamed module of loader ）
		Integer employeeId=employeesManagementService.selectEmployeeIdByName(employee.getName());
		passwordForm.setEmployeeId(employeeId);
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
				attributes.addFlashAttribute("errorMessage", "新規従業員のパスワード登録処理に失敗しました。<br>"
						+ "西暦を含めたハイフン区切りの生年月日が初期パスワードとして登録されていますが<br>"
						+ "任意のパスワードに変更してください。");
			} else {
				attributes.addFlashAttribute("errorMessage", "パスワードの更新処理に失敗しました");
			}
			return "redirect:/employees";
		}
	}
}
