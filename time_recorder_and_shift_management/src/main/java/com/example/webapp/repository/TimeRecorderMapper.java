package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.webapp.entity.ShiftAndTimestamp;

@Mapper
public interface TimeRecorderMapper {

	List<ShiftAndTimestamp> selectEmployeesByDate(@Param("date") LocalDate date);
	
	ShiftAndTimestamp selectShiftAndTimestampByEmployeeIdAndDate(@Param("employee_id") String id,@Param("date") LocalDate date);
	
	ShiftAndTimestamp selectShiftAndTimestampByShiftId(@Param("shift_id") Integer id);

	void start(@Param("shift_id") Integer id);
	
	void end(@Param("shift_id") Integer id);
}
