package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.form.ShiftAndTimestampForm;

public interface WorkHistoryManagementService {
	List<ShiftAndTimestamp> selectAllHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimestamp> selectHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedMembersByMonth(Integer targerMonth);
	ShiftAndTimestamp selectHistoryByHistoryId(Integer historyId);
	void updateHistory(ShiftAndTimestampForm updatedHistory);
}
