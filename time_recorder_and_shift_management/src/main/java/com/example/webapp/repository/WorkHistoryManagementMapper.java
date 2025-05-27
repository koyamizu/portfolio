package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.ShiftScheduleForm;

@Mapper
public interface WorkHistoryManagementMapper {
	
	List<ShiftSchedule> selectAllToDateByMonth(Integer targetMonth);

	List<ShiftSchedule> selectToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);

	List<Employee> selectByMonth(Integer targerMonth);

	ShiftSchedule selectByDateAndEmployeeId(Integer shiftId);

	void update(ShiftScheduleForm updatedHistory);
}
