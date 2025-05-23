package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.EntityForFullCalendar;
import com.example.webapp.form.ShiftScheduleEditForm;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftManagementServiceImpl implements ShiftManagementService {

	private final ShiftManagementMapper mapper;

	@Override
	public List<EntityForFullCalendar> selectThreeMonthShiftsByTargetMonth(Integer thisMonth) {
		return mapper.selectThreeMonthByTargetMonth(thisMonth);
	}

	@Override
	public List<EntityForFullCalendar> selectShiftRequestsByEmployeeId(Integer employeeId) {
		return mapper.selectByEmployeeId(employeeId);
	}

	@Override
	public void insertShiftRequests(List<ShiftScheduleEditForm> requests) {
		mapper.insertRequest(requests);
	}

	@Override
	public void deleteShiftRequestsByEmployeeId(Integer employeeId) {
		mapper.deleteRequestByEmployeeId(employeeId);
	}
	
	@Override
	public List<Employee> selectEmployeesNotSubmitRequests(){
		return mapper.selectEmployeesNotSubmitRequests();
	}

	@Override
	public List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth) {
		return mapper.selectOneMonthByTargetMonth(targetMonth);
	}

	@Override
	public List<EntityForFullCalendar> selectAllShiftRequests() {
		return mapper.selectAll();
	}

	@Override
	public void deleteShiftsByTargetMonth(Integer targetMonth) {
		mapper.deleteShiftByTargetMonth(targetMonth);
	}

	@Override
	public void insertNextMonthShifts(List<ShiftScheduleEditForm> newShifts) {
		mapper.insertShift(newShifts);
	}
}
