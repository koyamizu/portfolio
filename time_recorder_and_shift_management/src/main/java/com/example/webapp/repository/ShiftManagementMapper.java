package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.FullCalendarForm;

@Mapper
public interface ShiftManagementMapper {

	List<FullCalendarEntity> selectThreeMonthByTargetMonth(Integer targetMonth);

	List<FullCalendarEntity> selectByEmployeeId(Integer employeeId);
	
	List<ShiftSchedule> selectAllAfterTodayByEmployeeId(Integer employeeId);

	List<FullCalendarEntity> selectOneMonthByTargetMonth(Integer targetMonth);

	List<FullCalendarEntity> selectAll();

	List<Employee> selectEmployeesNotSubmitRequests();

	void insertRequest(List<FullCalendarForm> requests);

	void insertAdditionalRequest(List<FullCalendarForm> requests);

	void insertShift(List<FullCalendarForm> newShifts);

	void insertAdditionalShift(List<FullCalendarForm> newShifts);

	//	void deleteRequestByEmployeeId(Integer employeeId);

//	void deleteShiftsByTargetMonth(Integer targetMonth);
	
	void deleteByEmployeeId(List<FullCalendarForm> requests, Integer employeeId);

	void deleteByMonth(List<FullCalendarForm> newShifts,Integer targetMonth);

}
