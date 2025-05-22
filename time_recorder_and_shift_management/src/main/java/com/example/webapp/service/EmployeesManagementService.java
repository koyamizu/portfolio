package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;

public interface EmployeesManagementService {
	Employee selectEmployeeById(Integer employeeId);
	
	List<Employee> selectAllEmployees();
	
	List<Employee> selectAllIdAndName();
	
	Integer selectEmployeeIdByName(String name);
	
	void insertEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
	void deleteEmployeeById(Integer employeeId);
}
