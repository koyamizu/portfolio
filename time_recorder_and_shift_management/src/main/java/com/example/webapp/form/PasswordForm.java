package com.example.webapp.form;

import jakarta.validation.constraints.AssertTrue;

import lombok.Data;

@Data
public class PasswordForm {

	private Integer employeeId;
	private String newRawPassword;
	private String confirmPassword;
	
	@AssertTrue(message="入力パスワードと確認用パスワードが一致しません")
	public boolean isPasswordValid() {
		return newRawPassword.equals(confirmPassword);
	}
}
