package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftSchedule;

public interface TimeRecorderService {
	List<ShiftSchedule> selectEmployeesByDate(LocalDate date);

	ShiftSchedule selectByEmployeeId(Integer employeeId);

	ShiftSchedule selectByShiftId(Integer shiftId);
	
	void updateStartTimeByShiftId(Integer shiftId);

	void updateEndTimeByShiftId(Integer shiftId);
}
