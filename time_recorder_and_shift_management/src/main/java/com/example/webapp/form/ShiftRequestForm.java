package com.example.webapp.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftRequestForm {
	
	//シフトid
	private Integer id;
//	従業員Id
	private Integer employeeId;
	//従業員名
	private String title;
	//出勤日
	private String start;
	
	private Boolean isNew;
}
