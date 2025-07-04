package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;

@Mapper
public interface EmployeesManagementMapper {
	
	Employee selectById(Integer employeeId);
	
	List<Employee> selectAll();
	
	List<Employee> selectAllIdAndName();
	
	Integer selectIdByName(String name);
	
	void insert(Employee employee);
	
	void update(Employee employee);
	
	void deleteById(Integer employeeId);
	
	void setForeignKeyChecksOn();
	
	void setForeignKeyChecksOff();

	void deleteTimeRecords(Integer employeeId);

	void deleteShiftSchedules(Integer employeeId);

	void deleteShiftRequests(Integer employeeId);
}
