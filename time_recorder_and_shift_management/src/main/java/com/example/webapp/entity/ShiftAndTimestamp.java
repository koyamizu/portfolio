package com.example.webapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftAndTimestamp {
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
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
