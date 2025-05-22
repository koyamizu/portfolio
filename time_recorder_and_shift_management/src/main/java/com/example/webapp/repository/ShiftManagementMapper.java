package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;

@Mapper
public interface ShiftManagementMapper {
	
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
