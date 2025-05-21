package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;

public interface ShiftManagementService {
	List<EntityForFullCalendar> selectThreeMonthShiftsByTargetMonth(Integer targetMonth);
		
	List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId);
	
	void insertShiftRequests(List<ShiftScheduleEditForm> newShifts);
	
	void deleteRequestsByEmployeeId(Integer employeeId);
	
	List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth);

	List<EntityForFullCalendar> selectAllRequests();
	
	List<Employee> selectEmployeesNotSubmitRequests();

	void deleteShiftScheduleByTargetMonth(Integer targetMonth);

	void insertNextMonthShifts(List<ShiftScheduleEditForm> newShifts);
}
