package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;

@Mapper
public interface ShiftManagementMapper {
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
