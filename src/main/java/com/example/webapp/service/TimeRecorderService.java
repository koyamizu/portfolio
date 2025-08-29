package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.NoDataFoundException;

public interface TimeRecorderService {
	
	List<ShiftSchedule> getEmployeeWithClockTime() throws NoDataFoundException;

	Employee getEmployeeToClock(Integer employeeId) throws NoDataFoundException;

	void clockIn(Integer employeeId) throws DuplicateClockException;

	void clockOut(Integer employeeId) throws DuplicateClockException,InvalidClockException;
}
