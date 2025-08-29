package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;

@Mapper
@Repository
public interface TimeRecorderMapper {

	List<ShiftSchedule> selectToday();
	
	Employee selectEmployeeToClock(Integer employeeId);
	
	TimeRecord selectTodayTimeRecordByEmployeeId(Integer employeeId);

	void insert(Integer employeeId);
	
	void update(Integer employeeId);
}
