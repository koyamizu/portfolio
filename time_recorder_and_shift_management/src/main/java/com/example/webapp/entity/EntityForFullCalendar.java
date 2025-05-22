package com.example.webapp.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityForFullCalendar {
	//シフトid
	//Fullcalendarに表示する際に、「id」という名称である必要があるので、ここは「id」で固定
	private Integer id;
	
	private Integer employeeId;
	//従業員名
	private String title;
	//出勤日
	private String start;
	
	private String backgroundColor;
	
	private String borderColor;
	
	private String textColor;
}
