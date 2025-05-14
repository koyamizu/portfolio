package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.EntityForFullCalendar;

public interface ShiftManagementService {
	List<EntityForFullCalendar> selectThreeMonthShiftsByTargetMonth(Integer targetMonth);
	
//	Employee selectEmployeeById(Integer id);
	
	List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId);
	
	void insertShiftRequests(Integer employeeId,List<LocalDate> dates);
	
	void deleteRequestsByEmployeeId(Integer employeeId);
	
	List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth);

	List<EntityForFullCalendar> selectAllRequests();

	void deleteShiftScheduleByTargetMonth(Integer targetMonth);

	void insertShiftsOfNextMonth(List<LocalDate> dates);
}
