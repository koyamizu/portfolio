package com.example.webapp.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AbsenceApplication {
	
	private Integer id;
	
	private ShiftSchedule shiftSchedule;
	
	private Employee employee;
	
	private Absence absence;
	
	private String detail;
	
	private Boolean isApprove;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
