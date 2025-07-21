package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.form.TimeRecordForm;

public interface WorkHistoryManagementService {
	
	List<TimeRecord> getAllWorkHistoriesToDate(Integer targetMonth);
	
	List<TimeRecord> getPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	
	List<Employee> getWorkedEmployeesByMonth(Integer targerMonth);
	
	TimeRecordForm getWorkHistoryDetailByEmployeeIdAndDate(Integer employeeId, LocalDate date) throws NoDataFoundException;
	
	void updateWorkHistory(TimeRecordForm form) throws InvalidEditException;
}
