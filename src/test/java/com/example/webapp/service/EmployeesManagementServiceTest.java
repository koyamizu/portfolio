package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.EmployeeDataIntegrityViolationException;
import com.example.webapp.exception.ForeignKeyConstraintViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.impl.EmployeesManagementServiceImpl;
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
//サービスクラスのメソッドから返される値はデータベースから取得した値ではないので、メソッド内で値の加工がなかった場合は普通にisEqualsToでよい。
//また、Mockitoはデータベースと直接値をやりとりしているわけではないので、jdbcTemplateを使っても何も値は返って来ない
public class EmployeesManagementServiceTest {

	@InjectMocks
	EmployeesManagementServiceImpl service;

	@Mock
	EmployeesManagementMapper employeesManagementMapper;
	@Mock
	WorkHistoryManagementMapper workHistoryManagementMapper;
	@Mock
	ShiftManagementMapper shiftManagementMapper;
	@Mock
	AbsenceApplicationMapper absenceApplicationMapper;

	@Test
	void test_getAllEmployees() throws NoDataFoundException {
		List<Employee> expecteds = EmployeesManagementTestDataGenerator.getAllEmployees();
		doReturn(expecteds).when(employeesManagementMapper).selectAll();
		List<Employee> actuals = service.getAllEmployees();
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).containsExactlyInAnyOrderElementsOf(expecteds);
	}

	//		全ての従業員を抽出しようとしたが、戻り値が空だった時（ユーザーの操作というよりサーバー側のエラー）
	@Test
	void test_getAllEmployees_throws_NoDataException() {
		doReturn(null).when(employeesManagementMapper).selectAll();
		assertThrows(NoDataFoundException.class, () -> service.getAllEmployees());
	}

	@Test
	void test_getEmployee() throws InvalidEmployeeIdException {
		Employee expected = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		doReturn(expected).when(employeesManagementMapper).selectById(1001);
		Employee actual = service.getEmployee(1001);
		assertThat(actual).isEqualTo(expected);
	}

	//		存在しない従業員を抽出しようとした時
	@Test
	void test_getEmployee_throws_InvalidEmployeeIdException() {
		doReturn(null).when(employeesManagementMapper).selectById(1010);
		assertThrows(InvalidEmployeeIdException.class, () -> service.getEmployee(1010));
	}

	@Test
	void test_getEmployeeForm() throws InvalidEmployeeIdException {
		Employee expected = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		EmployeeForm expectedForm = EmployeeHelper.convertEmployeeForm(expected);
		doReturn(expected).when(employeesManagementMapper).selectById(1001);
		EmployeeForm actual = service.getEmployeeForm(1001);
		assertThat(actual).isEqualTo(expectedForm);
	}

	//		存在しない従業員を抽出しようとした時
	@Test
	void test_getEmployeeForm_throws_InvalidEmployeeeIdException() {
		doReturn(null).when(employeesManagementMapper).selectById(1010);
		assertThrows(InvalidEmployeeIdException.class, () -> service.getEmployeeForm(1010));
	}

	@Test
	void test_getAllEmployeeIdAndName() {
		List<Employee> expecteds = EmployeesManagementTestDataGenerator.getAllEmployeeIdAndName();
		doReturn(expecteds).when(employeesManagementMapper).selectAllIdAndName();
		List<Employee> actuals = service.getAllEmployeeIdAndName();
		assertThat(actuals).isEqualTo(expecteds);
	}

	@Test
	void test_getEmployeeIdByName() {
		Integer expected = 1001;
		doReturn(expected).when(employeesManagementMapper).selectIdByName("hoge");
		Integer actual = service.getEmployeeIdByName("hoge");
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	void test_insertEmployee() throws DuplicateEmployeeException, TooLongDataException {
		Employee dummy = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.foo);
		doNothing().when(employeesManagementMapper).insert(dummy);
		EmployeeForm dummyForm = EmployeeHelper.convertEmployeeForm(dummy);
		service.insertEmployee(dummyForm);
		verify(employeesManagementMapper).insert(any());
	}
	//		従業員を重複して登録しようとした時
//	Mockito、DataIntegrityViolationExceptionのクラスを投げることまでしかできない。その先のロジックまで調べたいときは、結合テストをするしかない。
//	サービスクラスのinsertEmployee()は、DataIntegrityViolationExceptionの中身によってふた通りの例外（DuplicateEmployeeExceptionとTooLongDataException）を出すが、
//	Mockitoが放出するのは中身が空のDataIntegrityViolationExceptionのクラスのみなので、insertEmployee()の中の条件分岐は不可。
//	Mockitoに何を渡そうがupdateEmployee()からはNullPointerExceptionが放出されるので、テストするのはひとパターンでいい。
	@Test
	void test_insertEmployee_throws_NullPointerException()  {
		Employee dummy = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		doThrow(DataIntegrityViolationException.class).when(employeesManagementMapper).insert(dummy);
		EmployeeForm dummyForm = EmployeeHelper.convertEmployeeForm(dummy);
		assertThrows(NullPointerException.class, () -> service.insertEmployee(dummyForm));
	}
	
	@Test
	void test_updateEmployee() throws DuplicateEmployeeException, TooLongDataException {
		Employee target = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.fuga);
		target.setName("hege");
		EmployeeForm targetForm = EmployeeHelper.convertEmployeeForm(target);

		doNothing().when(employeesManagementMapper).update(target);
		service.updateEmployee(targetForm);
		verify(employeesManagementMapper).update(target);
	}

//	Mockito、DataIntegrityViolationExceptionのクラスを投げることまでしかできない。その先のロジックまで調べたいときは、結合テストをするしかない。
//	サービスクラスのupdateEmployee()は、DataIntegrityViolationExceptionの中身によってふた通りの例外（DuplicateEmployeeExceptionとTooLongDataException）を出すが、
//	Mockitoが放出するのは中身が空のDataIntegrityViolationExceptionのクラスのみなので、updateEmployee()の中の条件分岐は不可。
//	Mockitoに何を渡そうがupdateEmployee()からはNullPointerExceptionが放出されるので、テストするのはひとパターンでいい。
	@Test
	void test_updateEmployee_throws_NullPointerException() {
		Employee target = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.fuga);
		target.setName("hoge");
		doThrow(DataIntegrityViolationException.class).when(employeesManagementMapper).update(target);
		EmployeeForm targetForm = EmployeeHelper.convertEmployeeForm(target);
		assertThrows(NullPointerException.class, () -> service.updateEmployee(targetForm));
	}

	@Test
	void test_deleteEmploye() throws InvalidEmployeeIdException,ForeignKeyConstraintViolationException {
		Employee target = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		doReturn(target).when(employeesManagementMapper).selectById(1001);
		doNothing().when(employeesManagementMapper).deleteById(1001);
		service.deleteEmployee(1001);
		verify(employeesManagementMapper).deleteById(1001);
	}
//	9/1 POSTメソッドなのでdelete/{employee-id}には直にアクセスできない。よって不要。
//	//存在しない従業員を削除しようとしたとき
//	@Test
//	void test_deleteEmploye_throws_InvalidEmployeeIdException() {
//		doReturn(null).when(employeesManagementMapper).selectById(1010);
//		assertThrows(InvalidEmployeeIdException.class, () -> service.deleteEmployee(1010));
//	}

	//勤務履歴やシフト予定が存在する従業員を削除しようとした時
	@Test
	void test_deleteEmployee_throws_EmployeeDataIntegerityException() {
		Employee target = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		doReturn(target).when(employeesManagementMapper).selectById(1001);
		doThrow(DataIntegrityViolationException.class).when(employeesManagementMapper).deleteById(1001);
		assertThrows(EmployeeDataIntegrityViolationException.class, () -> service.deleteEmployee(1001));
	}

	@Test
	void test_eraseAbsenceApplicationsTimeRecordsShiftRequestsAndShiftSchedules() throws ForeignKeyConstraintViolationException {
		doNothing().when(absenceApplicationMapper).deleteAll(1001);
		doNothing().when(workHistoryManagementMapper).deleteAll(1001);
		doNothing().when(shiftManagementMapper).deleteAllShiftRequests(1001);
		doNothing().when(shiftManagementMapper).deleteAllShiftSchedules(1001);
		service.eraseAbsenceApplicationsWorkHistoriesShiftRequestsAndShiftSchedules(1001);
		verify(absenceApplicationMapper).deleteAll(1001);
		verify(workHistoryManagementMapper).deleteAll(1001);
		verify(shiftManagementMapper).deleteAllShiftRequests(1001);
		verify(shiftManagementMapper).deleteAllShiftSchedules(1001);
	}

//	@Test
//	
//		FOREIGIN_KEY_CHECK=0が実行されていないために起こる例外
//	ShiftSchedulesより前にAbsenceApplicationsを消せばいいため、不要
//	void test_eraseAbsenceApplicationsTimeRecordsShiftRequestsAndShiftSchedules_throws_ForeignKeyViolationException()
//			throws ForeignKeyConstraintViolationException {
//		doThrow(DataIntegrityViolationException.class).when(shiftManagementMapper)
//				.deleteAllShiftSchedules(1001);
//		assertThrows(ForeignKeyConstraintViolationException.class,
//				() -> service.eraseAbsenceApplicationsTimeRecordsShiftRequestsAndShiftSchedules(1001));
//	}
}
