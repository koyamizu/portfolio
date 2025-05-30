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
	public Employee selectEmployeeById(Integer employeeId) {
		return mapper.selectById(employeeId);
	}

	@Override
	public List<Employee> selectAllEmployees(){
		return mapper.selectAll();
	}
	
	@Override
	public List<Employee> selectAllIdAndName(){
		return mapper.selectAllIdAndName();
	}
	
	@Override
	public Integer selectEmployeeIdByName(String name) {
		return mapper.selectIdByName(name);
	}
	
	@Override
	public void insertEmployee(Employee employee) {
		mapper.insert(employee);
	}
	
	@Override
	public void updateEmployee(Employee employee) {
		mapper.update(employee);
	}
	
	@Override
	public void deleteEmployeeById(Integer employeeId) {
		mapper.deleteById(employeeId);
	}
}
