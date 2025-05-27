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
public class TimeRecord {

//	打刻時刻ID
//	private Integer timeRecordId;
//	シフトID（外部キー（shift_schedulesのid））
//	private Integer shiftId;
	
	private LocalDate date;
	
	private Integer employeeId;
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
