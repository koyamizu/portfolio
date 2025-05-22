package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;

public interface WorkHistoryManagementService {
	List<ShiftAndTimeRecord> selectAllWorkHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimeRecord> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	ShiftAndTimeRecord selectWorkHistoryByShiftId(Integer shiftId);
	void updateWorkHistory(ShiftAndTimeRecordForm updatedHistory);
}
