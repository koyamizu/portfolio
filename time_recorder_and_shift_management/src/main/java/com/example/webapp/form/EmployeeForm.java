package com.example.webapp.form;

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
//	電話番号
	private String tel;
//	住所
	private String address;
	
	private Boolean isNew;
}
