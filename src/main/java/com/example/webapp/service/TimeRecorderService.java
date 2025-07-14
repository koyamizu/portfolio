package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.NoDataException;

public interface TimeRecorderService {
	
	List<ShiftSchedule> getEmployeeWithClockTime() throws NoDataException;

	Employee getEmployeeToClock(List<ShiftSchedule> todayMembersWithClockTime,Integer employeeId) throws NoDataException;

	void clockIn(Integer employeeId) throws DuplicateClockException;

	void clockOut(Integer employeeId) throws DuplicateClockException,InvalidClockException;
}
