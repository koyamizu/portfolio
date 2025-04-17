package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftAndTimestamp;

public interface TimeRecorderService {
	List<ShiftAndTimestamp> selectEmployeesByDate(LocalDate date);

	ShiftAndTimestamp selectShiftAndTimestampByEmployeeIdAndDate(String id,
			LocalDate date);

	void start(ShiftAndTimestamp shift);

	void end(ShiftAndTimestamp shift);
}
