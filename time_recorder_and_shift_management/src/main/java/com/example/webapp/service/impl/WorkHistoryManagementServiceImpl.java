package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.ShiftScheduleForm;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.WorkHistoryManagementService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHistoryManagementServiceImpl implements WorkHistoryManagementService {

	private final WorkHistoryManagementMapper mapper;
	@Override
	public List<ShiftSchedule> selectAllWorkHistoriesToDateByMonth(Integer targetMonth) {
		return mapper.selectAllToDateByMonth(targetMonth);
	}
	
	@Override
	public List<ShiftSchedule> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectToDateByEmployeeIdAndMonth(employeeId,targetMonth);
	}
	
	@Override
	public List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth){
		return mapper.selectByMonth(targerMonth);
	}
	
	@Override
	public ShiftSchedule selectWorkHistoryByShiftId(Integer shiftId) {
		return mapper.selectByShiftId(shiftId);
	}
	
	@Override
	public void updateWorkHistory(ShiftScheduleForm updatedHistory) {
		mapper.update(updatedHistory);
	}
}
