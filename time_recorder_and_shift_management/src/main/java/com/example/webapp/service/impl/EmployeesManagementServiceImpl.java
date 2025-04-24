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
	
	private final EmployeesManagementMapper mapper;
	@Override
	public Employee selectEmployeeById(Integer employee_id) {
		return mapper.selectEmployeeById(employee_id);
	}

	@Override
	public List<Employee> selectAllEmployees(){
		return mapper.selectAllEmployees();
	}
	
	@Override
	public void insertEmployee(Employee employee) {
		mapper.insertEmployee(employee);
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		mapper.updateEmployee(employee);
	}
	
	@Override
	public void deleteEmployee(Integer id) {
		mapper.deleteEmployee(id);
	}
}
