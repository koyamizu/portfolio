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
public class ShiftAndTimeRecord {
	//シフトid
	private Integer shiftId;
	//従業員
	private Employee employee;
	//出勤日
	private LocalDate date;
//	開始打刻時間
	private LocalTime start;
//	終了打刻時刻
	private LocalTime end;
	
	private LocalTime workTime;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
