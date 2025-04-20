package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.EmployeesManagementService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeesManagementServiceImpl  implements EmployeesManagementService{
	
	private final EmployeesManagementMapper employeesManagementMapper;
	@Override
	public Employee selectEmployeeById(String employee_id) {
		return employeesManagementMapper.selectEmployeeById(employee_id);
	}

	@Override
	public List<Employee> selectAllEmployees(){
		return employeesManagementMapper.selectAllEmployees();
	}
}
