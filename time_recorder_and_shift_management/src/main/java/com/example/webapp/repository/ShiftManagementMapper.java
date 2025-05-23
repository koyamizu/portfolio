package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;

@Mapper
public interface ShiftManagementMapper {
	
	List<EntityForFullCalendar> selectThreeMonthByTargetMonth(Integer targetMonth);

	List<EntityForFullCalendar> selectByEmployeeId(Integer employeeId);

	List<EntityForFullCalendar> selectOneMonthByTargetMonth(Integer targetMonth);
	
	List<EntityForFullCalendar> selectAll();

	List<Employee> selectEmployeesNotSubmitRequests();

	void insertRequest(List<ShiftScheduleEditForm> requests);

	void insertShift(List<ShiftScheduleEditForm> newShifts);

	void deleteRequestByEmployeeId(Integer employeeId);
	
	void deleteShiftByTargetMonth(Integer targetMonth);

}
