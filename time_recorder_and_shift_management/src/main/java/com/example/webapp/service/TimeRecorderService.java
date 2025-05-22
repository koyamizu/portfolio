package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftAndTimeRecord;

public interface TimeRecorderService {
	List<ShiftAndTimeRecord> selectEmployeesByDate(LocalDate date);

	ShiftAndTimeRecord selectShiftByEmployeeIdAndDate(Integer employeeId,
			LocalDate date);

	ShiftAndTimeRecord selectTimeRecordByShiftId(Integer shiftId);
	
	void updateStartTimeByShiftId(Integer shiftId);

	void updateEndTimeByShiftId(Integer shiftId);
}
