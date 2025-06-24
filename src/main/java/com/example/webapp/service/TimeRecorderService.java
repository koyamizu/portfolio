package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.NoDataException;

public interface TimeRecorderService {
	
	List<ShiftSchedule> getEmployeeList(LocalDate date) throws NoDataException;

	Employee getEmployeeToClock(Integer employeeId) throws NoDataException;

//	TimeRecord getTodayPersonalTimeRecordData(Integer employeeId);
	
	void clockIn(Integer employeeId) throws DuplicateClockException;

	void clockOut(Integer employeeId) throws DuplicateClockException,InvalidClockException;
}
