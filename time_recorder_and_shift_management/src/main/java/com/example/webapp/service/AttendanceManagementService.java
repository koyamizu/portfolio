package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.ShiftAndTimestamp;

public interface AttendanceManagementService {
	List<ShiftAndTimestamp> selectAllHistoriesToDate(Integer targetMonth);
	List<ShiftAndTimestamp> selectHistoryToDateByEmployeeId(Integer employeeId);
}
