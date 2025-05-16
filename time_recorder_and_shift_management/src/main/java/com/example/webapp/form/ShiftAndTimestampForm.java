package com.example.webapp.form;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.webapp.entity.Employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftAndTimestampForm {
	//シフトid
	private Integer id;
	//従業員
	private Employee employee;
	//出勤日
	private LocalDate date;
	//出勤時刻
	private LocalTime start;
	//退勤時刻
	private LocalTime end;
	
	private LocalTime workTime;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
