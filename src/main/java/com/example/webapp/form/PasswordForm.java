package com.example.webapp.form;

import java.util.Objects;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PasswordForm {

	private Integer employeeId;
	@NotNull(message="入力パスワードが空欄です")
	private String newRawPassword;
	@NotNull(message="確認用パスワードが空欄です")
	private String confirmPassword;
	private Boolean isNew;
	
	@AssertTrue(message="入力パスワードと確認用パスワードが一致しません")
	public boolean isPasswordValid() {
//		return newRawPassword.equals(confirmPassword);
		return Objects.equals(newRawPassword,confirmPassword);
	}
}
