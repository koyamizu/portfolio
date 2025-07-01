package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityException;
import com.example.webapp.exception.ForeignKeyViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.EmployeeForm;

public interface EmployeesManagementService {
	EmployeeForm getEmployeeForm(Integer employeeId) throws InvalidEmployeeIdException;
	
	Employee getEmployee(Integer employeeId) throws InvalidEmployeeIdException;
	
	List<Employee> getAllEmployees() throws NoDataException;
	
	List<Employee> getAllEmployeeIdAndName();
	
	Integer getEmployeeIdByName(String name);
	
	void insertEmployee(EmployeeForm newEmployee) throws DuplicateEmployeeException;
	
	void updateEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException;
	
	void deleteEmployee(Integer employeeId) throws InvalidEmployeeIdException,EmployeeDataIntegrityException;

	void eraseShiftSchedulesAndTimeRecordsAndShiftRequests(Integer employeeId) throws ForeignKeyViolationException;
}
