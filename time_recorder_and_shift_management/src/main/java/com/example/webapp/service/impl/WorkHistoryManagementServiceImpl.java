package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.WorkHistoryManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHistoryManagementServiceImpl implements WorkHistoryManagementService {

	private final WorkHistoryManagementMapper mapper;
	@Override
	public List<ShiftAndTimeRecord> selectAllWorkHistoriesToDateByMonth(Integer targetMonth) {
		return mapper.selectAllWorkHistoriesToDateByMonth(targetMonth);
	}
	
	@Override
	public List<ShiftAndTimeRecord> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId,targetMonth);
	}
	
	@Override
	public List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth){
		return mapper.selectWorkedEmployeesByMonth(targerMonth);
	}
	
	@Override
	public ShiftAndTimeRecord selectWorkHistoryByShiftId(Integer shiftId) {
		return mapper.selectWorkHistoryByShiftId(shiftId);
	}
	
	@Override
	public void updateWorkHistory(ShiftAndTimeRecordForm updatedHistory) {
		mapper.updateWorkHistory(updatedHistory);
	}
}
