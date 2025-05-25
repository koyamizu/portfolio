package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.form.ShiftScheduleForm;

public interface WorkHistoryManagementService {
	List<ShiftSchedule> selectAllWorkHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftSchedule> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	ShiftSchedule selectWorkHistoryByShiftId(Integer shiftId);
	void updateWorkHistory(ShiftScheduleForm updatedHistory);
}
