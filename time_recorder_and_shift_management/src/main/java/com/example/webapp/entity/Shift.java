package com.example.webapp.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shift {
	//シフトid
	private Integer id;
	//出勤日
	private LocalDate date;
	//従業員
	private Employee employee;
	
}
