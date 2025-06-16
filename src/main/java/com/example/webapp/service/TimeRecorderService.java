package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;

public interface TimeRecorderService {
	
	List<ShiftSchedule> getEmployeeList(LocalDate date) throws NoDataException;

	ShiftSchedule getTodayPersonalShiftData(Integer employeeId) throws InvalidEmployeeIdException,NoDataException;

	TimeRecord getTodayPersonalTimeRecordData(Integer employeeId);
	
	void clockIn(Integer employeeId) throws DuplicateClockException;

	void clockOut(Integer employeeId) throws DuplicateClockException,InvalidClockException;
}
