package com.example.webapp.form;

import java.util.Objects;

import jakarta.validation.constraints.AssertTrue;

import lombok.Data;

@Data
public class PasswordForm {

	private Integer employeeId;
	private String newRawPassword;
	private String confirmPassword;
	private Boolean isNew;
	
	@AssertTrue(message="入力パスワードと確認用パスワードが一致しません")
	public boolean isPasswordValid() {
//		return newRawPassword.equals(confirmPassword);
		return Objects.equals(newRawPassword,confirmPassword);
	}
}
