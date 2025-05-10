package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.ShiftAndTimestamp;

public interface AttendanceManagementService {
	List<ShiftAndTimestamp> selectAllHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimestamp> selectHistoryToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
}
