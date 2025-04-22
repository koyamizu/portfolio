package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;

public interface EmployeesManagementService {
	Employee selectEmployeeById(Integer Id);
	
	List<Employee> selectAllEmployees();
	
	void insertEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
	void deleteEmployee(Integer id);
}
