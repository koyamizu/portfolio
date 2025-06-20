package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftManagementServiceImpl implements ShiftManagementService {

	private final ShiftManagementMapper shiftManagementMapper;
	private final EmployeesManagementMapper employeeManagementMapper;

	@Override
	public List<FullCalendarEntity> getThreeMonthShifts(Integer targetMonth) {
		return shiftManagementMapper.selectThreeMonthByTargetMonth(targetMonth);
	}

	@Override
	public List<FullCalendarEntity> getPersonalShiftRequests(Integer employeeId) {
		return shiftManagementMapper.selectByEmployeeId(employeeId);
	}

	@Override
	public List<ShiftSchedule> selectAllShiftsAfterTodayByEmployeeId(Integer employeeId) {
		List<Integer> employeeIds=employeeManagementMapper.selectAllIdAndName().stream().map(e->e.getEmployeeId()).toList();
		if(!employeeIds.contains(employeeId)) {
			throw new RuntimeException("そのIDを持つ従業員は存在しません");
		}
		return shiftManagementMapper.selectAllAfterTodayByEmployeeId(employeeId);
	}
	
	@Override
	public void insertShiftRequests(List<FullCalendarForm> requests) {
		shiftManagementMapper.insertRequest(requests);
	}

	//	@Override
	//	public void deleteShiftRequestsByEmployeeId(Integer employeeId) {
	//		mapper.deleteRequestByEmployeeId(employeeId);
	//	}

	@Override
	public List<Employee> selectEmployeesNotSubmitRequests() {
		return shiftManagementMapper.selectNotSubmit();
	}

	@Override
	public List<FullCalendarEntity> selectOneMonthShiftsByTargetMonth(Integer targetMonth) {
		return shiftManagementMapper.selectOneMonthByTargetMonth(targetMonth);
	}

	@Override
	public List<FullCalendarEntity> selectAllShiftRequests() {
		return shiftManagementMapper.selectAll();
	}

//	@Override
//	public void deleteShiftsByTargetMonth(Integer targetMonth) {
//		mapper.deleteShiftByTargetMonth(targetMonth);
//	}

	@Override
	public void insertNextMonthShifts(List<FullCalendarForm> newShifts) {
		shiftManagementMapper.insertShift(newShifts);
	}

	@Override
	public void insertAdditionalRequest(List<FullCalendarForm> requests) {
		shiftManagementMapper.insertAdditionalRequest(requests);
	}

	@Override
	public void deleteByEmployeeId(List<FullCalendarForm> requests, Integer employeeId) {
		shiftManagementMapper.deleteByEmployeeId(requests, employeeId);
	}

	@Override
	public void insertAdditionalShift(List<FullCalendarForm> newShifts) {
		shiftManagementMapper.insertAdditionalShift(newShifts);
		
	}

	@Override
	public void deleteByMonth(List<FullCalendarForm> newShifts,Integer targetMonth) {
		shiftManagementMapper.deleteByMonth(newShifts,targetMonth);
		
	}

}
