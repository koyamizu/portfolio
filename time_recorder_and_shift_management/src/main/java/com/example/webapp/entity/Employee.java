package com.example.webapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

//	従業員id
	private Integer employeeId;
//	従業員パスワード
	private String password;
//	従業員名
	private String name;
//	生年月日
	private LocalDate birth;
//	電話番号
	private String tel;
//	住所
	private String address;
//	権限
	private Role authority;
//	作成日時
	private LocalDateTime created_at;
//	更新日時
	private LocalDateTime updated_at;
//	パスワード更新日時
	private LocalDateTime password_updated_at;
}
