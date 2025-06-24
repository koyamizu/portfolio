package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.TimeRecordForm;

public interface WorkHistoryManagementService {
	
	List<TimeRecord> getAllWorkHistoriesToDate(Integer targetMonth);
	
	List<TimeRecord> gettPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	
	TimeRecordForm getWorkHistoryDetailByEmployeeIdAndDate(Integer employeeId, LocalDate date) throws NoDataException;
	
	void updateWorkHistory(TimeRecordForm form) throws InvalidEditException;
}
