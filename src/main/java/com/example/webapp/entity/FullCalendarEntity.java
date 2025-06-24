package com.example.webapp.entity;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullCalendarEntity {
	//シフトid
	//Fullcalendarに表示する際に、「id」という名称である必要があるので、ここは「id」で固定
	private Integer shiftId;
	
	private Employee employee;
	//出勤日と出勤予定時刻
	private LocalDate start;
//	出勤日と退勤予定時刻
	private LocalTime scheduledStart;
	
	private LocalTime scheduledEnd;
}
