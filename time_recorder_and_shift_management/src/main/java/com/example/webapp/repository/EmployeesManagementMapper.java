package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.webapp.entity.Employee;

@Mapper
public interface EmployeesManagementMapper {
	Employee selectEmployeeById(@Param("employee_id") String id);
	
	List<Employee> selectAllEmployees();
}
