package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;

@Mapper
public interface WorkHistoryManagementMapper {
	
	List<TimeRecord> selectAllToDateByMonth(Integer targetMonth);

	List<TimeRecord> selectToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);

	List<Employee> selectByMonth(Integer targerMonth);

	TimeRecord selectByEmployeeIdAndDate(Integer employeeId, LocalDate date);

	void update(TimeRecord updatedHistory);
}
