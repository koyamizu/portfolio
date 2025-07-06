package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.webapp.common.EmployeeTestDataOld;
import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityException;
import com.example.webapp.exception.ForeignKeyViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.impl.EmployeesManagementServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeesManagementServiceTest {

	@InjectMocks
	EmployeesManagementServiceImpl service;

	@Mock
	EmployeesManagementMapper mapper;
	@Mock
	WorkHistoryManagementMapper workHistoryManagementMapper;

	private EmployeeTestDataOld data = new EmployeeTestDataOld();

	@Test
	void test_getAllEmployees() throws NoDataException {
		List<Employee> employees = data.createAllEmployees();
		doReturn(employees).when(mapper).selectAll();
		List<Employee> actuals = service.getAllEmployees();
		assertThat(actuals.size()).isEqualTo(employees.size());
	}

	@Test
	void test_getAllEmployees_throws_NoDataExeption() {
		doReturn(null).when(mapper).selectAll();
		assertThrows(NoDataException.class, () -> service.getAllEmployees());
	}

	@Test
	void test_getEmployee() throws InvalidEmployeeIdException {
		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(mapper).selectById(1001);
		Employee actual = service.getEmployee(1001);
		assertThat(actual).isEqualTo(yoshizuka);
	}

	@Test
	void test_getEmployee_throws_InvalidEmployeeeIdException() throws InvalidEmployeeIdException {
		doReturn(null).when(mapper).selectById(1010);
		assertThrows(InvalidEmployeeIdException.class, () -> service.getEmployee(1010));
	}

	@Test
	void test_getEmployeeForm() throws InvalidEmployeeIdException {
		Employee yoshizuka = data.getYoshizuka();
		EmployeeForm yoshizukaForm = EmployeeHelper.convertEmployeeForm(yoshizuka);
		doReturn(yoshizuka).when(mapper).selectById(1001);
		EmployeeForm actual = service.getEmployeeForm(1001);
		assertThat(actual).isEqualTo(yoshizukaForm);
	}

	@Test
	void test_getEmployeeForm_throws_InvalidEmployeeeIdException() {
		doReturn(null).when(mapper).selectById(1010);
		assertThrows(InvalidEmployeeIdException.class, () -> service.getEmployeeForm(1010));
	}

	@Test
	void test_getAllEmployeeIdAndName() {
		List<Employee> employees = data.createTestEmployeeIdAndName();
		doReturn(employees).when(mapper).selectAllIdAndName();
		List<Employee> actuals = service.getAllEmployeeIdAndName();
		assertThat(actuals).isEqualTo(employees);
	}

	@Test
	void test_getEmployeeIdByName() {
		Integer id = 1001;
		doReturn(id).when(mapper).selectIdByName("吉塚");
		Integer actual = service.getEmployeeIdByName("吉塚");
		assertThat(actual).isEqualTo(id);
	}

	@Test
	void test_insertEmployee() throws DuplicateEmployeeException {
		Employee newEmployee = data.getChihaya();
		doNothing().when(mapper).insert(newEmployee);
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(newEmployee);
		service.insertEmployee(form);
		verify(mapper).insert(any());
	}

	@Test
	void test_insertEmployee_throws_DuplicateEmployeeException() throws DuplicateEmployeeException {
		Employee newEmployee = data.getYoshizuka();
		doThrow(DataIntegrityViolationException.class).when(mapper).insert(newEmployee);
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(newEmployee);
		assertThrows(DuplicateEmployeeException.class, () -> service.insertEmployee(form));
	}

	@Test
	void test_updateEmployee() throws DuplicateEmployeeException, InvalidEmployeeIdException {
		Employee original = data.getYoshizuka();
		original.setName("吉津鹿");
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(original);

		doNothing().when(mapper).update(original);
		service.updateEmployee(form);

		ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
		//		実際にmapperに渡された値を検証できる
		verify(mapper).update(captor.capture());
		Employee updated = captor.getValue();

		assertThat(updated.getName()).isEqualTo("吉津鹿");
	}

	@Test
	void test_deleteEmploye() throws InvalidEmployeeIdException, EmployeeDataIntegrityException {
		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(mapper).selectById(1001);
		doNothing().when(mapper).deleteById(1001);
		service.deleteEmployee(1001);
	}
	
	@Test
	void test_deleteEmploye_throws_InvalidEmployeeIdException() {
		doReturn(null).when(mapper).selectById(1010);
		assertThrows(InvalidEmployeeIdException.class,()->service.deleteEmployee(1010));
	}
	
	@Test
	void test_deleteEmployee_throws_EmployeeDataIntegerityException() {
		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(mapper).selectById(1001);
		doThrow(DataIntegrityViolationException.class).when(mapper).deleteById(1001);
		assertThrows(EmployeeDataIntegrityException.class,()->service.deleteEmployee(1001));
	}

	@Test
	void test_eraseShiftSchedulesAndTimeRecordsAndShiftRequests() throws ForeignKeyViolationException {
		service.eraseShiftSchedulesAndTimeRecordsAndShiftRequests(1001);
	}
	
	@Test
	void test_eraseShiftSchedulesAndTimeRecordsAndShiftRequests_throws_ForeignKeyViolationException() throws ForeignKeyViolationException {
		doThrow(DataIntegrityViolationException.class).when(workHistoryManagementMapper).deleteAllTimeRecords(1001);
		assertThrows(ForeignKeyViolationException.class,()->service.eraseShiftSchedulesAndTimeRecordsAndShiftRequests(1001));
	}
}
