package com.example.webapp.form;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceApplicationForm {
	
	private Integer applicationId;
	
	private ShiftSchedule shiftSchedule;
	
	private Employee employee;
	
	private AbsenceReason absenceReason;
	
	private String detail;
	
	private Boolean isApprove;
}
