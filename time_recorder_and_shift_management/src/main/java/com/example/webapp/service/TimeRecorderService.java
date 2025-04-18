package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftAndTimestamp;

public interface TimeRecorderService {
	List<ShiftAndTimestamp> selectEmployeesByDate(LocalDate date);

	ShiftAndTimestamp selectShiftAndTimestampByEmployeeIdAndDate(String employee_id,
			LocalDate date);

	ShiftAndTimestamp selectShiftAndTimestampByShiftId(Integer shift_id);
	
	void start(Integer shift_id);

	void end(Integer shift_id);
}
