package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.WorkHistoryManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHistoryManagementServiceImpl implements WorkHistoryManagementService {

	private final WorkHistoryManagementMapper mapper;
	@Override
	public List<TimeRecord> selectAllWorkHistoriesToDateByMonth(Integer targetMonth) {
		return mapper.selectAllToDateByMonth(targetMonth);
	}
	
	@Override
	public List<TimeRecord> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectToDateByEmployeeIdAndMonth(employeeId,targetMonth);
	}
	
	@Override
	public List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth){
		return mapper.selectByMonth(targerMonth);
	}
	
	@Override
	public TimeRecord selectWorkHistoryByEmployeeIdAndDate(Integer employeeId, LocalDate date) {
		return mapper.selectByEmployeeIdAndDate(employeeId, date);
	}
	
	@Override
	public void updateWorkHistory(TimeRecord updatedHistory) {
		mapper.update(updatedHistory);
	}
}
