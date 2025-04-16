package com.example.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

//	従業員id
	private String id;
//	従業員名
	private String name;
//	電話番号
	private String tel;
//	住所
	private String address;
//	従業員とシフトとの1対1の関係
	private Shift shift;
}
