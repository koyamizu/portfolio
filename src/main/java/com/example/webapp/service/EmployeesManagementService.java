package com.example.webapp.service;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityViolationException;
import com.example.webapp.exception.ForeignKeyConstraintViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.form.EmployeeForm;

public interface EmployeesManagementService {
	EmployeeForm getEmployeeForm(Integer employeeId) throws InvalidEmployeeIdException;
	
	Employee getEmployee(Integer employeeId) throws InvalidEmployeeIdException;
	
	List<Employee> getAllEmployees() throws NoDataFoundException;
	
	List<Employee> getAllEmployeeIdAndName();
	
	Integer getEmployeeIdByName(String name);
	
	void insertEmployee(EmployeeForm newEmployee) throws DuplicateEmployeeException, TooLongDataException;
	
	void updateEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException, TooLongDataException;
	
	void deleteEmployee(Integer employeeId) throws InvalidEmployeeIdException,EmployeeDataIntegrityViolationException;

	void eraseShiftSchedulesAndTimeRecordsAndShiftRequests(Integer employeeId) throws ForeignKeyConstraintViolationException;
}
