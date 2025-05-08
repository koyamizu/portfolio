package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;

@Mapper
public interface ShiftManagementMapper {
	List<EntityForFullCalendar> selectAllShifts();
	
	Employee selectEmployeeById(Integer id);
	
	List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId);
	
	void insertShiftRequests(@Param("employee_id")Integer employeeId,List<LocalDate> dates);
	
	void deleteRequestsByEmployeeId(Integer employeeId);
}
