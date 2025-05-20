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
		return mapper.selectThreeMonthShiftsByTargetMonth(thisMonth);
	}

	//	@Override
	//	public 	Employee selectEmployeeById(Integer id) {
	//		return mapper.selectEmployeeById(id);
	//	}

	@Override
	public List<EntityForFullCalendar> selectRequestsByEmployeeId(Integer employeeId) {
		return mapper.selectRequestsByEmployeeId(employeeId);
	}

//	@Override
//	public void insertShiftRequests(Integer employeeId, List<LocalDate> dates) {
//		mapper.insertShiftRequests(employeeId, dates);
//	}
	@Override
	public void insertShiftRequests(List<ShiftScheduleEditForm> newShifts) {
		mapper.insertShiftRequests(newShifts);
	}

	@Override
	public void deleteRequestsByEmployeeId(Integer employeeId) {
		mapper.deleteRequestsByEmployeeId(employeeId);
	}
	
	@Override
	public List<Employee> selectEmployeesNotSubmitRequests(){
		return mapper.selectEmployeesNotSubmitRequests();
	}

	@Override
	public List<EntityForFullCalendar> selectOneMonthShiftsByTargetMonth(Integer targetMonth) {
		return mapper.selectOneMonthShiftsByTargetMonth(targetMonth);
	}

	@Override
	public List<EntityForFullCalendar> selectAllRequests() {
		return mapper.selectAllRequests();
	}

	@Override
	public void deleteShiftScheduleByTargetMonth(Integer targetMonth) {
		mapper.deleteShiftScheduleByTargetMonth(targetMonth);
	}

	@Override
	public void insertNextMonthShifts(List<ShiftScheduleEditForm> newShifts) {
		mapper.insertNextMonthShifts(newShifts);
	}
}
