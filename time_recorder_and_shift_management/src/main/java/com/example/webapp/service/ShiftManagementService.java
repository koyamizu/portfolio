package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.form.FullCalendarForm;

public interface ShiftManagementService {
	List<FullCalendarEntity> selectThreeMonthShiftsByTargetMonth(Integer targetMonth);

	List<FullCalendarEntity> selectShiftRequestsByEmployeeId(Integer employeeId);

	List<FullCalendarEntity> selectOneMonthShiftsByTargetMonth(Integer targetMonth);
	
	List<FullCalendarEntity> selectAllShiftRequests();
	
	List<Employee> selectEmployeesNotSubmitRequests();
	
	void insertShiftRequests(List<FullCalendarForm> requests);

	void insertAdditionalRequest(List<FullCalendarForm> requests);
	
	void insertNextMonthShifts(List<FullCalendarForm> newShifts);

	void insertAdditionalShift(List<FullCalendarForm> newShifts);

	//	void deleteShiftRequestsByEmployeeId(Integer employeeId);

//	void deleteShiftsByTargetMonth(Integer targetMonth);

	void deleteByEmployeeId(List<FullCalendarForm> requests, Integer employeeId);
	
	void deleteByMonth(List<FullCalendarForm> newShifts,Integer targetMonth);
}
