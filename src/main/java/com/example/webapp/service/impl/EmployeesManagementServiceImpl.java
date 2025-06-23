package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.ForeiginKeyViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.EmployeesManagementService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeesManagementServiceImpl implements EmployeesManagementService {

	private final EmployeesManagementMapper mapper;

	@Override
	public List<Employee> getAllEmployees() throws NoDataException {
		List<Employee> allEmployees = mapper.selectAll();

		if (CollectionUtils.isEmpty(allEmployees)) {
			throw new NoDataException("従業員一覧が取得できませんでした。");
		}
		return allEmployees;
	}
	
	@Override
	public Employee getEmployee(Integer employeeId) throws InvalidEmployeeIdException {
		Employee employee = mapper.selectById(employeeId);
		if (employee.equals(null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		return employee;
	}

	@Override
	public EmployeeForm getEmployeeForm(Integer employeeId) throws InvalidEmployeeIdException {
		Employee target = mapper.selectById(employeeId);
		if (target.equals(null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(target);
		return form;
	}

	@Override
	public List<Employee> getAllIdAndName() {
		return mapper.selectAllIdAndName();
	}

	@Override
	public Integer getEmployeeIdByName(String name) {
		return mapper.selectIdByName(name);
	}

	@Override
	public void insertEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			mapper.insert(employee);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmployeeException("従業員名が重複しています");
		}
	}

	@Override
	public void updateEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			mapper.update(employee);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmployeeException("従業員名が重複しています");
		}
	}

	@Override
	public void deleteEmployee(Integer employeeId) throws InvalidEmployeeIdException, ForeiginKeyViolationException {
		Employee target = mapper.selectById(employeeId);
		if (target.equals(null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		try {
			mapper.deleteById(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new ForeiginKeyViolationException("そのIDをもつ従業員はシフトに登録されているので削除できません");
		}
	}
}
