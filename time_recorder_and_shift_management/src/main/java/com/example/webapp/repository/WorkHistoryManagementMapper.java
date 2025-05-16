package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.form.ShiftAndTimestampForm;

@Mapper
public interface WorkHistoryManagementMapper {
	List<ShiftAndTimestamp> selectAllHistoriesToDateByMonth(Integer targetMonth);
	List<ShiftAndTimestamp> selectHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);
	List<Employee> selectWorkedMembersByMonth(Integer targerMonth);
	ShiftAndTimestamp selectHistoryByShiftId(Integer shiftId);
	void updateHistory(ShiftAndTimestampForm updatedHistory);
}
