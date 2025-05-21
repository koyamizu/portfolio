package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Employee;

@Mapper
public interface EmployeesManagementMapper {
	Employee selectEmployeeById(Integer id);
	
	List<Employee> selectAllEmployees();
	
	Integer selectEmployeeIdByName(String name);
	
	void insertEmployee(Employee employee);
	
	void updateEmployee(Employee employee);
	
	void deleteEmployeeById(Integer id);
}
