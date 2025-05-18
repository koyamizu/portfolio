package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.entity.ShiftAndTimestamp;

@Mapper
public interface ShiftManagementMapper {
	List<EntityForFullCalendar> selectThreeMonthShiftsByTargetMonth(Integer targetMonth);

	//	Employee selectEmployeeById(Integer id);

	List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId);

	void insertShiftRequests(Integer employeeId, List<LocalDate> dates);

	void deleteRequestsByEmployeeId(Integer employeeId);

	List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth);

	List<ShiftAndTimestamp> selectAllRequests();
	
	List<Employee> selectEmployeesNotSubmitRequests();

	void deleteShiftScheduleByTargetMonth(Integer targetMonth);

	void insertShiftsOfNextMonth(List<LocalDate> dates);
}
