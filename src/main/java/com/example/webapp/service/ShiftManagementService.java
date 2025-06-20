package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftCreateContainer;
import com.example.webapp.entity.ShiftEditContainer;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateShiftException;
import com.example.webapp.exception.InvalidEditException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ShiftManagementService {
	List<FullCalendarEntity> getThreeMonthShifts(Integer targetMonth);

	List<FullCalendarEntity> getPersonalShiftRequests(Integer employeeId);
	
	List<ShiftSchedule> getAllShiftsAfterToday(Integer employeeId);

	List<FullCalendarEntity> getOneMonthShifts(Integer targetMonth);
	
//	List<FullCalendarEntity> selectAllShiftRequests();
	
//	List<Employee> selectEmployeesNotSubmitRequests();
	
	ShiftCreateContainer initializeShiftCreateContainerFields();
	
	ShiftEditContainer initializeShiftEditContainerFields(Integer month);
	
	void registerShiftRequests(String requestsStr, Integer employeeId) throws JsonMappingException, JsonProcessingException;
	
	void updateShiftRequests(String requestsStr, Integer employeeId) throws JsonMappingException, JsonProcessingException, DuplicateShiftException;

//	void insertAdditionalRequest(List<FullCalendarForm> additionals);
	
	void createNextMonthShifts(String newShiftsStr) throws JsonMappingException, JsonProcessingException;

//	void insertAdditionalShift(List<FullCalendarForm> newShifts);
	
	void updateShiftSchedules(String shiftSchedulesStr, Integer month) throws JsonMappingException, JsonProcessingException, InvalidEditException;

	//	void deleteShiftRequestsByEmployeeId(Integer employeeId);

//	void deleteShiftsByTargetMonth(Integer targetMonth);

//	void deleteRequests(List<FullCalendarForm> requests, Integer employeeId);
	
//	void deleteByMonth(List<FullCalendarForm> newShifts,Integer targetMonth);
}
