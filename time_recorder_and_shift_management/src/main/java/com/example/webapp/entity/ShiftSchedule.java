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
public class ShiftSchedule {

//	シフトID
	private Integer shiftId;
//	従業員ID（外部キー（employeesのid））
	private Integer employeeId;
//	出勤予定日
	private LocalDate date;
//	開始予定時間
	private LocalTime scheduledStart;
//	終了予定時間
	private LocalTime scheduledEnd;
//	作成時間
	private LocalDateTime createdAt;
}
