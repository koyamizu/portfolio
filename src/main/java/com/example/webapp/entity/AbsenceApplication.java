package com.example.webapp.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceApplication {
	
	private Integer applicationId;
	
//	shiftScheduleにもEmployeeはある
	private ShiftSchedule shiftSchedule;
	
	//nameしか使ってないからこれはString employeeNameだけでいいと思う。
	private Employee employee;
	
	//name(categoryに変更予定)しか使ってないから、これもnameだけでいいと思う。
	private AbsenceReason absenceReason;
	
	private String detail;
	
	private Boolean isApprove;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
