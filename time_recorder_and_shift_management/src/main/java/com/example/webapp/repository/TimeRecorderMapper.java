package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.ShiftSchedule;

@Mapper
public interface TimeRecorderMapper {

	List<ShiftSchedule> selectEmployeeByDate(LocalDate date);
	
	//ShiftSchedule型
	ShiftSchedule selectByEmployeeId(Integer employeeId);
	
	ShiftSchedule selectByShiftId(Integer shiftId);

	void insert(Integer shiftId);
	
	void updateEndTimeByShiftId(Integer shiftId);
}
