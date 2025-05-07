package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.form.PasswordForm;
import com.example.webapp.service.PasswordService;
import com.example.webapp.utility.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reissue_password")
public class PasswordController {

	private final PasswordService service;

	@GetMapping("execute")
	public String reissue(PasswordForm form, RedirectAttributes attributes) {
		String newBcryptPassword = PasswordGenerator.generatePassword(form.getNewRawPassword());
		try {
			service.updatePasswordByEmployeeId(form.getEmployeeId(), newBcryptPassword);
			attributes.addFlashAttribute("message", "従業員ID:"+form.getEmployeeId()+"のパスワードが更新されました");
			return "redirect:/employees";
		} catch (Exception e) {
			attributes.addFlashAttribute("errorMessage", "パスワードの更新処理に失敗しました");
			return "redirect:/employees";
		}
	}
}
