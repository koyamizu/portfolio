package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;

public interface TimeRecorderService {
	List<ShiftSchedule> selectEmployeesByDate(LocalDate date);

	ShiftSchedule selectByEmployeeId(Integer employeeId);

	TimeRecord selectByDateAndEmployeeId(Integer employeeId, LocalDate date);
	
	void updateStartTimeByEmployeIdAndDate(Integer employeeId, LocalDate date);

	void updateEndTimeByEmployeeIdAndDate(Integer employeeId, LocalDate date);
}
