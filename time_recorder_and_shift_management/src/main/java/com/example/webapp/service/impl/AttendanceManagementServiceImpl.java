package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.repository.AttendanceManagementMapper;
import com.example.webapp.service.AttendanceManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AttendanceManagementServiceImpl implements AttendanceManagementService {

	private final AttendanceManagementMapper mapper;
	@Override
	public List<ShiftAndTimestamp> selectAllHistoriesToDateByMonth(Integer targetMonth) {
		return mapper.selectAllHistoriesToDateByMonth(targetMonth);
	}
	
	@Override
	public List<ShiftAndTimestamp> selectHistoryToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectHistoryToDateByEmployeeIdAndMonth(employeeId,targetMonth);
	}
}
