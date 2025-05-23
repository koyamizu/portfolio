package com.example.webapp.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShiftAndTimeRecord {
	//シフトid
	private Integer shiftId;
	//従業員
	private Employee employee;
	//出勤日
	private LocalDate date;
	//出勤時刻
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime start;
	//退勤時刻
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime end;
	
	private LocalTime workTime;
	
	private LocalDateTime created_at;
	
	private LocalDateTime updated_at;
}
