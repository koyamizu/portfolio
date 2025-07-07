package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityException;
import com.example.webapp.exception.ForeignKeyViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.EmployeesManagementService;
import com.google.common.base.Objects;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeesManagementServiceImpl implements EmployeesManagementService {

	private final EmployeesManagementMapper employeeManagementMapper;
	private final WorkHistoryManagementMapper workHistoryManagementMapper;
	private final ShiftManagementMapper shiftManagementMapper;

	@Override
	public List<Employee> getAllEmployees() throws NoDataException {
		List<Employee> allEmployees = employeeManagementMapper.selectAll();

		if (CollectionUtils.isEmpty(allEmployees)) {
			throw new NoDataException("従業員一覧が取得できませんでした。");
		}
		return allEmployees;
	}

	@Override
	public Employee getEmployee(Integer employeeId) throws InvalidEmployeeIdException {
		Employee employee = employeeManagementMapper.selectById(employeeId);
		if (Objects.equal(employee, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		return employee;
	}

	@Override
	public EmployeeForm getEmployeeForm(Integer employeeId) throws InvalidEmployeeIdException {
		Employee target = employeeManagementMapper.selectById(employeeId);
		if (Objects.equal(target, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(target);
		return form;
	}

	@Override
	public List<Employee> getAllEmployeeIdAndName() {
		return employeeManagementMapper.selectAllIdAndName();
	}

	@Override
	public Integer getEmployeeIdByName(String name) {
		return employeeManagementMapper.selectIdByName(name);
	}

	@Override
	public void insertEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			employeeManagementMapper.insert(employee);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmployeeException("従業員名が重複しています");
		}
	}

	@Override
	public void updateEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			employeeManagementMapper.update(employee);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateEmployeeException("従業員名が重複しています");
		}
	}

	@Override
	public void deleteEmployee(Integer employeeId) throws InvalidEmployeeIdException, EmployeeDataIntegrityException {
		Employee target = employeeManagementMapper.selectById(employeeId);
		if (Objects.equal(target, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		try {
			employeeManagementMapper.deleteById(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new EmployeeDataIntegrityException("勤怠履歴の存在する従業員です", employeeId);
		}
	}

	@Transactional
	@Override
	public void eraseShiftSchedulesAndTimeRecordsAndShiftRequests(Integer employeeId)
			throws ForeignKeyViolationException {
		employeeManagementMapper.setForeignKeyChecksOff();
		try {
			workHistoryManagementMapper.deleteAllTimeRecords(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyViolationException("Happened in deleteTimeRecords:"+e.getMessage());
		}try {
			shiftManagementMapper.deleteAllShiftSchedules(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyViolationException("Happened in deleteShiftSchedules:"+e.getMessage());
		}try {
			shiftManagementMapper.deleteAllShiftRequests(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyViolationException("Happened in deleteShiftRequests:"+e.getMessage());
		} finally {
			employeeManagementMapper.setForeignKeyChecksOn();
		}
	}
}
