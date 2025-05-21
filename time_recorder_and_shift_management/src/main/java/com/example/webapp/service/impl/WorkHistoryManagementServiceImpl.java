package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.form.ShiftAndTimestampForm;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.WorkHistoryManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHistoryManagementServiceImpl implements WorkHistoryManagementService {

	private final WorkHistoryManagementMapper mapper;
	@Override
	public List<ShiftAndTimestamp> selectAllWorkHistoriesToDateByMonth(Integer targetMonth) {
		return mapper.selectAllHistoriesToDateByMonth(targetMonth);
	}
	
	@Override
	public List<ShiftAndTimestamp> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectHistoriesToDateByEmployeeIdAndMonth(employeeId,targetMonth);
	}
	
	@Override
	public List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth){
		return mapper.selectWorkedMembersByMonth(targerMonth);
	}
	
	@Override
	public ShiftAndTimestamp selectWorkHistoryByShiftId(Integer historyId) {
		return mapper.selectHistoryByShiftId(historyId);
	}
	
	@Override
	public void updateHistory(ShiftAndTimestampForm updatedHistory) {
		mapper.updateHistory(updatedHistory);
	}
}
