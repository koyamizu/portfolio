package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;

public interface EmployeesManagementService {
	Employee selectEmployeeById(String Id);
	
	List<Employee> selectAllEmployees();
}
