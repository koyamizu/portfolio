package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.FullCalendarDisplay;
import com.example.webapp.entity.ShiftCreateContainer;
import com.example.webapp.entity.ShiftEditContainer;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateShiftException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface ShiftManagementService {
	List<FullCalendarDisplay> getThreeMonthShifts(Integer targetMonth) throws NoDataFoundException;

	List<FullCalendarDisplay> getPersonalShiftRequests(Integer employeeId) ;
	
	List<ShiftSchedule> getAllShiftsAfterToday(Integer employeeId);

	List<FullCalendarDisplay> getOneMonthShifts(Integer targetMonth);
		
	ShiftCreateContainer initializeShiftCreateContainerFields();
	
	ShiftEditContainer initializeShiftEditContainerFields(Integer month);
	
	void registerShiftRequests(String requestsStr, Integer employeeId) throws JsonMappingException, JsonProcessingException;
	
	void updateShiftRequests(String requestsStr, Integer employeeId) throws JsonMappingException, JsonProcessingException, DuplicateShiftException;
	
	void createNextMonthShifts(String newShiftsStr) throws JsonMappingException, JsonProcessingException;
	
	void updateShiftSchedules(String shiftSchedulesStr, Integer month) throws JsonMappingException, JsonProcessingException, InvalidEditException;
}
