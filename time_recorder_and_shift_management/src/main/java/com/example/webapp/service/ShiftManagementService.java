package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;

public interface ShiftManagementService {
	List<EntityForFullCalendar> selectAllShifts();
	
	Employee selectEmployeeById(Integer id);
	
	List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId);
	
	void insertShiftRequests(Integer employeeId,List<LocalDate> dates);
	
	void deleteRequestsByEmployeeId(Integer employeeId);
}
