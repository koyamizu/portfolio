package com.example.webapp.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityForFullCalendar {
	//シフトid
	private Integer id;
	//従業員名
	private String title;
	//出勤日
	private String start;
}
