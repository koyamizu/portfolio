package com.example.webapp.service_integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Sql("../repository/EmployeesManagementMapperTest.sql")
@Slf4j
@Transactional
public class EmployeesManagementServiceIntegrationTest {

	@Autowired
	EmployeesManagementService service;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

//	@Test
//	void test_getEmployeeForm() throws InvalidEmployeeIdException {
//		Employee yoshizuka = EmployeesManagementTestDataGenerator.getYoshizuka();
//		EmployeeForm actual = service.getEmployeeForm(1001);
//		assertThat(actual.getEmployeeId()).isEqualTo(yoshizuka.getEmployeeId());
//		assertThat(encoder.matches(yoshizuka.getPassword(), actual.getPassword())).isTrue();
//		assertThat(actual.getName()).isEqualTo(yoshizuka.getName());
//		assertThat(actual.getBirth()).isEqualTo(yoshizuka.getBirth());
//		assertThat(actual.getTel()).isEqualTo(yoshizuka.getTel());
//		assertThat(LocalDate.parse(actual.getAddress())).isEqualTo(yoshizuka.getAddress());
//		assertThat(actual.getAuthority()).isEqualTo(yoshizuka.getAuthority());
//	}
//
//	@Test
//	void test_getAllEmployees() throws NoDataException {
//		List<Employee> actuals = service.getAllEmployees();
//
//		assertThat(actuals.size()).isEqualTo(6);
//
//		Employee yoshizuka = EmployeesManagementTestDataGenerator.getYoshizuka();
//
//		assertThat(actuals.get(0).getEmployeeId()).isEqualTo(yoshizuka.getEmployeeId());
//		assertThat(encoder.matches(yoshizuka.getPassword(), actuals.get(0).getPassword())).isTrue();
//		assertThat(actuals.get(0).getName()).isEqualTo(yoshizuka.getName());
//		assertThat(actuals.get(0).getBirth()).isEqualTo(yoshizuka.getBirth());
//		assertThat(actuals.get(0).getTel()).isEqualTo(yoshizuka.getTel());
//		assertThat(actuals.get(0).getAddress()).isEqualTo(yoshizuka.getAddress());
//		assertThat(actuals.get(0).getAuthority()).isEqualTo(yoshizuka.getAuthority());
//	}
//
//	@Test
//	void testSelectAllIdAndName() {
//		List<Employee> actuals = service.getAllEmployeeIdAndName();
//
//		assertThat(actuals.size()).isEqualTo(6);
//
//		Employee yoshizuka = EmployeesManagementTestDataGenerator.getYoshizuka();
//
//		assertThat(actuals).extracting(Employee::getEmployeeId).contains(yoshizuka.getEmployeeId());
//		assertThat(actuals).extracting(Employee::getName).contains(yoshizuka.getName());
//	}
//
//	@Test
//	void testSelectEmployeeIdByName() {
//		Integer id=service.getEmployeeIdByName("吉塚");
//		assertThat(id).isEqualTo(1001);
//	}
//
//	@Test
//	void testInsertEmployee() throws InvalidEmployeeIdException, DuplicateEmployeeException {
//		Employee chihaya=EmployeesManagementTestDataGenerator.getChihaya();
//		EmployeeForm chihayaForm=EmployeeHelper.convertEmployeeForm(chihaya);
//		service.insertEmployee(chihayaForm);
//		EmployeeForm confirm=service.getEmployeeForm(1007);
//		assertThat(confirm.getPassword()).isEqualTo(chihaya.getPassword());
//		assertThat(confirm.getName()).isEqualTo(chihaya.getName());
//		assertThat(LocalDate.parse(confirm.getBirth())).isEqualTo(chihaya.getBirth());
//		assertThat(confirm.getTel()).isEqualTo(chihaya.getTel());
//		assertThat(confirm.getAddress()).isEqualTo(chihaya.getAddress());
//		assertThat(confirm.getAuthority()).isEqualTo(chihaya.getAuthority());
//	}

//	@Test
//	void testUpdateEmployee() throws InvalidEmployeeIdException, DuplicateEmployeeException {
//		EmployeeForm kogaForm=service.getEmployeeForm(1002);
//		log.info("名前変更前："+kogaForm.getName());
//		kogaForm.setName("鹿部");
//		service.updateEmployee(kogaForm);
//		EmployeeForm exKoga=service.getEmployeeForm(1002);
//		assertThat(exKoga.getName()).isEqualTo("鹿部");
//	}
	
	@Test
	void test_updateEmployee_throws_DuplicateEmployeeException() {
		Employee target=EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		EmployeeForm targetForm=EmployeeHelper.convertEmployeeForm(target);
		targetForm.setName("fuga");
		assertThrows(DuplicateEmployeeException.class,()->service.updateEmployee(targetForm));
	}
	
	@Test
	void test_updateEmployee_throws_TooLongDataException() {
		Employee target=EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		EmployeeForm targetForm=EmployeeHelper.convertEmployeeForm(target);
		targetForm.setAddress("福岡県古賀市天神1-1hogehogehogehogehogehogehogehogehogehoge");//51文字
		assertThrows(TooLongDataException.class,()->service.updateEmployee(targetForm));
	}

//	@Test
//	void testDeleteEmployeeById() throws InvalidEmployeeIdException,EmployeeDataIntegrityException {
//		assertThat(service.getEmployeeForm(1001)).isNotNull();
//		service.deleteEmployee(1001);
//		assertThat(service.getEmployeeForm(1001)).isNull();
//	}
}
