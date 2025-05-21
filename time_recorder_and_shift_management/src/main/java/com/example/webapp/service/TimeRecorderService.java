package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftAndTimestamp;

public interface TimeRecorderService {
	List<ShiftAndTimestamp> selectEmployeesByDate(LocalDate date);

	ShiftAndTimestamp selectShiftByEmployeeIdAndDate(Integer employee_id,
			LocalDate date);

	ShiftAndTimestamp selectTimestampByShiftId(Integer shift_id);
	
	void updateStartTimeByShiftId(Integer shift_id);

	void updateEndTimeByShiftId(Integer shift_id);
}
