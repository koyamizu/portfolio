package com.example.webapp.repository;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.ShiftAndTimeRecord;

@Mapper
public interface TimeRecorderMapper {

	List<ShiftAndTimeRecord> selectEmployeesByDate(LocalDate date);
	
	ShiftAndTimeRecord selectShiftByEmployeeIdAndDate(Integer employeeId,LocalDate date);
	
	ShiftAndTimeRecord selectTimeRecordByShiftId(Integer ShiftId);

	void updateStartTimeByShiftId(Integer shiftId);
	
	void updateEndTimeByShiftId(Integer shiftId);
}
