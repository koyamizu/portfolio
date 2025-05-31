package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;

@Mapper
public interface TimeRecorderMapper {

	List<ShiftSchedule> selectEmployeeByDate(LocalDate date);
	
	//ShiftSchedule型
	ShiftSchedule selectByEmployeeId(Integer employeeId);
	
	TimeRecord selectByDateAndEmployeeId(Integer employeeId, LocalDate date);

	void insert(Integer employeeId, LocalDate date);
	
	void update(Integer employeeId, LocalDate date);
}
