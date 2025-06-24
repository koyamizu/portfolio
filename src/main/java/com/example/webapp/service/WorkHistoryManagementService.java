package com.example.webapp.service;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;

public interface WorkHistoryManagementService {
	
	List<TimeRecord> getAllWorkHistoriesToDate(Integer targetMonth);
	
	List<TimeRecord> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	
	TimeRecord selectWorkHistoryByEmployeeIdAndDate(Integer employeeId, LocalDate date);
	
	void updateWorkHistory(TimeRecord updatedHistory);
}
