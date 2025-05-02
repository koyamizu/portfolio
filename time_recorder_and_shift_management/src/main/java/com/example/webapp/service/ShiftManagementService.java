package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;

public interface ShiftManagementService {
	List<EntityForFullCalendar> selectAllShiftByYearMonth(LocalDate date);
	
	Employee selectEmployeeById(Integer id);
	
	void insertShiftRequests(Integer employeeId,List<LocalDate> dates);
}
