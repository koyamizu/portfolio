package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.form.ShiftAndTimestampForm;

public interface WorkHistoryManagementService {
	List<ShiftAndTimestamp> selectAllWorkHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimestamp> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	ShiftAndTimestamp selectWorkHistoryByShiftId(Integer historyId);
	void updateHistory(ShiftAndTimestampForm updatedHistory);
}
