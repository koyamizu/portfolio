package com.example.webapp.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeStamp {
	//打刻時刻id
	private Integer id;
//	//従業員id
//	private String employee_id;
//	シフトID
//	private Integer shift_id;
	
//	打刻時刻とシフトとの1対1の関係
	private Shift shift;
	//出勤時刻
	private LocalDateTime start;
	//退勤時刻
	private LocalDateTime end;
}
