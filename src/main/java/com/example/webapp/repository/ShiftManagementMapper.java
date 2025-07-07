package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftSchedule;

@Mapper
public interface ShiftManagementMapper {

	List<FullCalendarEntity> selectThreeMonthByTargetMonth(Integer targetMonth);

	List<FullCalendarEntity> selectRequestByEmployeeId(Integer employeeId);
	
	List<ShiftSchedule> selectAllAfterTodayByEmployeeId(Integer employeeId);

	List<FullCalendarEntity> selectOneMonthByTargetMonth(Integer targetMonth);

	List<FullCalendarEntity> selectAllRequests();

	List<Employee> selectNotSubmit();

	void insertRequest(List<FullCalendarEntity> requests);

	void insertShift(List<FullCalendarEntity> newShifts);

	void deleteOldRequestByEmployeeId(List<FullCalendarEntity> newRequests, Integer employeeId);

	void deleteOldShiftByMonth(List<FullCalendarEntity> newShifts,Integer targetMonth);
	
	void deleteAllShiftSchedules(Integer employeeId);
	
	void deleteAllShiftRequests(Integer employeeId);

}
