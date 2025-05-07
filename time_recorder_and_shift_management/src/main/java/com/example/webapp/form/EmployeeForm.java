package com.example.webapp.form;

import jakarta.validation.constraints.Pattern;

import com.example.webapp.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {
//	従業員id
	private Integer id;
//	従業員パスワード
	private String password;
//	従業員名
	private String name;
//	生年月日
	@Pattern(regexp="^[0-9]{4}/(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])$")
	private String birth;
//	電話番号
	@Pattern(regexp="^0[789]0-[0-9]{4}-[0-9]{4}$")
	private String tel;
//	住所
	private String address;
	
	private Role authority;
	
	private Boolean isNew;
}
