package com.example.webapp.service.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityViolationException;
import com.example.webapp.exception.ForeignKeyConstraintViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.EmployeesManagementService;
import com.google.common.base.Objects;
import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeesManagementServiceImpl implements EmployeesManagementService {

	private final EmployeesManagementMapper employeesManagementMapper;
	private final WorkHistoryManagementMapper workHistoryManagementMapper;
	private final ShiftManagementMapper shiftManagementMapper;

	@Override
	public List<Employee> getAllEmployees() throws NoDataFoundException {
		List<Employee> allEmployees = employeesManagementMapper.selectAll();

		if (CollectionUtils.isEmpty(allEmployees)) {
			throw new NoDataFoundException("従業員一覧が取得できませんでした。");
		}
		return allEmployees;
	}

	@Override
	public Employee getEmployee(Integer employeeId) throws InvalidEmployeeIdException {
		Employee employee = employeesManagementMapper.selectById(employeeId);
		if (Objects.equal(employee, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		return employee;
	}

	@Override
	public EmployeeForm getEmployeeForm(Integer employeeId) throws InvalidEmployeeIdException {
		Employee target = employeesManagementMapper.selectById(employeeId);
		if (Objects.equal(target, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(target);
		return form;
	}

	@Override
	public List<Employee> getAllEmployeeIdAndName() {
		return employeesManagementMapper.selectAllIdAndName();
	}

	@Override
	public Integer getEmployeeIdByName(String name) {
		return employeesManagementMapper.selectIdByName(name);
	}

	@Override
	public void insertEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException, TooLongDataException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			employeesManagementMapper.insert(employee);
		} catch (DataIntegrityViolationException e) {
			if (e.getCause().getClass().equals(SQLIntegrityConstraintViolationException.class)) {
				throw new DuplicateEmployeeException("従業員名が重複しています");
			}
			if (e.getCause().getClass().equals(MysqlDataTruncation.class)) {
				throw new TooLongDataException(e.getCause().getMessage());
			}
		}
	}

	@Override
	public void updateEmployee(EmployeeForm employeeForm) throws DuplicateEmployeeException, TooLongDataException {
		Employee employee = EmployeeHelper.convertEmployee(employeeForm);
		try {
			employeesManagementMapper.update(employee);
		} catch (DataIntegrityViolationException e) {
			if (e.getCause().getClass().equals(SQLIntegrityConstraintViolationException.class)) {
				throw new DuplicateEmployeeException("従業員名が重複しています");
			}
			if (e.getCause().getClass().equals(MysqlDataTruncation.class)) {
				throw new TooLongDataException(e.getCause().getMessage());
			}
		}
	}

	@Override
	public void deleteEmployee(Integer employeeId) throws InvalidEmployeeIdException, EmployeeDataIntegrityViolationException {
		Employee target = employeesManagementMapper.selectById(employeeId);
		if (Objects.equal(target, null)) {
			throw new InvalidEmployeeIdException("そのIDをもつ従業員データは存在しません");
		}
		try {
			employeesManagementMapper.deleteById(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new EmployeeDataIntegrityViolationException("勤怠履歴やシフトデータの存在する従業員です", employeeId);
		}
	}

	@Transactional
	@Override
	public void eraseShiftSchedulesAndTimeRecordsAndShiftRequests(Integer employeeId)
			throws ForeignKeyConstraintViolationException {
		//外部キー制約違反はdeleteAllShiftSchdulesByEmployeeIdのみで起こりうる
		employeesManagementMapper.setForeignKeyChecksOff();
		try {
			workHistoryManagementMapper.deleteAllTimeRecords(employeeId);
			shiftManagementMapper.deleteAllShiftSchedulesByEmployeeId(employeeId);
			shiftManagementMapper.deleteAllShiftRequestsByEmployeeId(employeeId);
		} catch (DataIntegrityViolationException e) {
			throw new ForeignKeyConstraintViolationException("外部キー制約違反でシフトが削除できませんでした:" + e.getMessage());
		} finally {
			employeesManagementMapper.setForeignKeyChecksOn();
		}
	}
}
