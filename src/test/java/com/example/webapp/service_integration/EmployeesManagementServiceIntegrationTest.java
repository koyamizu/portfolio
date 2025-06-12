package com.example.webapp.service_integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.common.EmployeeTestData;
import com.example.webapp.entity.Employee;
import com.example.webapp.service.EmployeesManagementService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Sql("../repository/EmployeesManagementMapperTest.sql")
@Slf4j
@Transactional
public class EmployeesManagementServiceIntegrationTest {

	@Autowired
	EmployeesManagementService service;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	private EmployeeTestData data = new EmployeeTestData();

	@Test
	void testSelectEmployeeById() {
		Employee yoshizuka = data.getYoshizuka();
		Employee actual = service.selectEmployeeById(1001);
		assertThat(actual.getEmployeeId()).isEqualTo(yoshizuka.getEmployeeId());
		assertThat(encoder.matches(yoshizuka.getPassword(), actual.getPassword())).isTrue();
		assertThat(actual.getName()).isEqualTo(yoshizuka.getName());
		assertThat(actual.getBirth()).isEqualTo(yoshizuka.getBirth());
		assertThat(actual.getTel()).isEqualTo(yoshizuka.getTel());
		assertThat(actual.getAddress()).isEqualTo(yoshizuka.getAddress());
		assertThat(actual.getAuthority()).isEqualTo(yoshizuka.getAuthority());
	}

	@Test
	void testSelectAllEmployees() {
		List<Employee> actuals = service.selectAllEmployees();

		assertThat(actuals.size()).isEqualTo(6);

		Employee yoshizuka = data.getYoshizuka();

		assertThat(actuals.get(0).getEmployeeId()).isEqualTo(yoshizuka.getEmployeeId());
		assertThat(encoder.matches(yoshizuka.getPassword(), actuals.get(0).getPassword())).isTrue();
		assertThat(actuals.get(0).getName()).isEqualTo(yoshizuka.getName());
		assertThat(actuals.get(0).getBirth()).isEqualTo(yoshizuka.getBirth());
		assertThat(actuals.get(0).getTel()).isEqualTo(yoshizuka.getTel());
		assertThat(actuals.get(0).getAddress()).isEqualTo(yoshizuka.getAddress());
		assertThat(actuals.get(0).getAuthority()).isEqualTo(yoshizuka.getAuthority());
	}

	@Test
	void testSelectAllIdAndName() {
		List<Employee> actuals = service.selectAllIdAndName();

		assertThat(actuals.size()).isEqualTo(6);

		Employee yoshizuka = data.getYoshizuka();

		assertThat(actuals).extracting(Employee::getEmployeeId).contains(yoshizuka.getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(yoshizuka.getName());
	}

	@Test
	void testSelectEmployeeIdByName() {
		Integer id=service.selectEmployeeIdByName("吉塚");
		assertThat(id).isEqualTo(1001);
	}

	@Test
	void testInsertEmployee() {
		Employee chihaya=data.getChihaya();
		service.insertEmployee(chihaya);
		Employee confirm=service.selectEmployeeById(1007);
		assertThat(confirm.getPassword()).isEqualTo(chihaya.getPassword());
		assertThat(confirm.getName()).isEqualTo(chihaya.getName());
		assertThat(confirm.getBirth()).isEqualTo(chihaya.getBirth());
		assertThat(confirm.getTel()).isEqualTo(chihaya.getTel());
		assertThat(confirm.getAddress()).isEqualTo(chihaya.getAddress());
		assertThat(confirm.getAuthority()).isEqualTo(chihaya.getAuthority());
	}

	@Test
	void testUpdateEmployee() {
		Employee koga=service.selectEmployeeById(1002);
		log.info("名前変更前："+koga.getName());
		koga.setName("鹿部");
		service.updateEmployee(koga);
		Employee exKoga=service.selectEmployeeById(1002);
		assertThat(exKoga.getName()).isEqualTo("鹿部");
	}

	@Test
	void testDeleteEmployeeById() {
		assertThat(service.selectEmployeeById(1001)).isNotNull();
		service.deleteEmployeeById(1001);
		assertThat(service.selectEmployeeById(1001)).isNull();
	}
}
