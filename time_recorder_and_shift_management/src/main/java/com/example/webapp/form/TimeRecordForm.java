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
public class TimeRecordForm {

	private LocalDate date;
//	従業員
	private Employee employee;
//	開始の打刻時間
	private LocalTime clockIn;
//	終了の打刻時間
	private LocalTime clockOut;
//	実働時間
	private LocalTime workTime;
//	作成時刻
	private LocalDateTime createdAt;
//	更新時刻
	private LocalDateTime updatedAt;
}
