package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftManagementServiceImpl implements ShiftManagementService {

	private final ShiftManagementMapper mapper;

	@Override
	public List<FullCalendarEntity> selectThreeMonthShiftsByTargetMonth(Integer thisMonth) {
		return mapper.selectThreeMonthByTargetMonth(thisMonth);
	}

	@Override
	public List<FullCalendarEntity> selectShiftRequestsByEmployeeId(Integer employeeId) {
		return mapper.selectByEmployeeId(employeeId);
	}

	@Override
	public List<ShiftSchedule> selectAllShiftsAfterTodayByEmployeeId(Integer employeeId) {
		return mapper.selectAllAfterTodayByEmployeeId(employeeId);
	}
	
	@Override
	public void insertShiftRequests(List<FullCalendarForm> requests) {
		mapper.insertRequest(requests);
	}

	//	@Override
	//	public void deleteShiftRequestsByEmployeeId(Integer employeeId) {
	//		mapper.deleteRequestByEmployeeId(employeeId);
	//	}

	@Override
	public List<Employee> selectEmployeesNotSubmitRequests() {
		return mapper.selectNotSubmit();
	}

	@Override
	public List<FullCalendarEntity> selectOneMonthShiftsByTargetMonth(Integer targetMonth) {
		return mapper.selectOneMonthByTargetMonth(targetMonth);
	}

	@Override
	public List<FullCalendarEntity> selectAllShiftRequests() {
		return mapper.selectAll();
	}

//	@Override
//	public void deleteShiftsByTargetMonth(Integer targetMonth) {
//		mapper.deleteShiftByTargetMonth(targetMonth);
//	}

	@Override
	public void insertNextMonthShifts(List<FullCalendarForm> newShifts) {
		mapper.insertShift(newShifts);
	}

	@Override
	public void insertAdditionalRequest(List<FullCalendarForm> requests) {
		mapper.insertAdditionalRequest(requests);
	}

	@Override
	public void deleteByEmployeeId(List<FullCalendarForm> requests, Integer employeeId) {
		mapper.deleteByEmployeeId(requests, employeeId);
	}

	@Override
	public void insertAdditionalShift(List<FullCalendarForm> newShifts) {
		mapper.insertAdditionalShift(newShifts);
		
	}

	@Override
	public void deleteByMonth(List<FullCalendarForm> newShifts,Integer targetMonth) {
		mapper.deleteByMonth(newShifts,targetMonth);
		
	}

}
