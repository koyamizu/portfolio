package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.ShiftAndTimestamp;

@Mapper
public interface TimeRecorderMapper {

	List<ShiftAndTimestamp> selectEmployeesByDate(LocalDate date);
	
	ShiftAndTimestamp selectShiftByEmployeeIdAndDate(Integer employeeId,LocalDate date);
	
	ShiftAndTimestamp selectTimestampByShiftId(Integer ShiftId);

	void updateStartTimeByShiftId(Integer shiftId);
	
	void updateEndTimeByShiftId(Integer shiftId);
}
