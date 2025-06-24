package com.example.webapp.form;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceApplicationForm {
	
	private Integer applicationId;
	
	@Valid
	private ShiftSchedule shiftSchedule;
	
	private Employee employee;
	
	@Valid
	private AbsenceReason absenceReason;
	
	@NotNull(message="詳細を入力してください")
	private String detail;
	
	private Boolean isApprove;
}
