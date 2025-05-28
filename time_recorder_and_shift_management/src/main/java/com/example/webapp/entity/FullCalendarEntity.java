package com.example.webapp.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullCalendarEntity {
	//シフトid
	//Fullcalendarに表示する際に、「id」という名称である必要があるので、ここは「id」で固定
	private Integer id;
	
	private Integer employeeId;
	//従業員名
	private String title;
	//出勤日と出勤予定時刻
	private String start;
//	出勤日と退勤予定時刻
	private String scheduledStart;
	
	private String scheduledEnd;
	
	private String backgroundColor;
	
	private String borderColor;
	
	private String textColor;
	
//	private String display;
}
