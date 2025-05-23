package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftAndTimeRecord;
import com.example.webapp.form.ShiftAndTimeRecordForm;

@Mapper
public interface WorkHistoryManagementMapper {
	
	List<ShiftAndTimeRecord> selectAllToDateByMonth(Integer targetMonth);

	List<ShiftAndTimeRecord> selectToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth);

	List<Employee> selectByMonth(Integer targerMonth);

	ShiftAndTimeRecord selectByShiftId(Integer shiftId);

	void update(ShiftAndTimeRecordForm updatedHistory);
}
