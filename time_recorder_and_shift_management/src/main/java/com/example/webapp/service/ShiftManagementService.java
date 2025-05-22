package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;

public interface ShiftManagementService {
	List<EntityForFullCalendar> selectThreeMonthShiftsByTargetMonth(Integer targetMonth);
		
	List<EntityForFullCalendar> selectShiftRequestsByEmployeeId(Integer employeeId);
	
	void insertShiftRequests(List<ShiftScheduleEditForm> requests);
	
	void deleteShiftRequestsByEmployeeId(Integer employeeId);
	
	List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth);

	List<EntityForFullCalendar> selectAllShiftRequests();
	
	List<Employee> selectEmployeesNotSubmitRequests();

	void deleteShiftsByTargetMonth(Integer targetMonth);

	void insertNextMonthShifts(List<ShiftScheduleEditForm> newShifts);
}
