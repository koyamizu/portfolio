package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.ShiftAndTimestamp;

@Mapper
public interface AttendanceManagementMapper {
	List<ShiftAndTimestamp> selectAllHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimestamp> selectHistoryToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
}
