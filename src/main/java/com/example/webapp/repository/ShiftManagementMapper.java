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

	List<Employee> selectNotSubmit();

	void insertRequest(List<FullCalendarEntity> requests);

	void insertShift(List<FullCalendarEntity> newShifts);

	void deleteByEmployeeId(List<FullCalendarEntity> requests, Integer employeeId);

	void deleteByMonth(List<FullCalendarEntity> newShifts,Integer targetMonth);

}
