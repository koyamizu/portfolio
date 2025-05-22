package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;

@Mapper
public interface WorkHistoryManagementMapper {
	List<ShiftAndTimeRecord> selectAllWorkHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimeRecord> selectPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedEmployeesByMonth(Integer targerMonth);
	ShiftAndTimeRecord selectWorkHistoryByShiftId(Integer shiftId);
	void updateWorkHistory(ShiftAndTimeRecordForm updatedHistory);
}
