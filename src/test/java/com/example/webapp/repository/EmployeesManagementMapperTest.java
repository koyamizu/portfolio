package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("EmployeesManagementMapperTest.sql")
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class EmployeesManagementMapperTest {

	@Autowired
	private EmployeesManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	void test_selectById() {
		Employee expected = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		Employee actual = mapper.selectById(1001);
		assertThat(actual.getEmployeeId()).isEqualTo(expected.getEmployeeId());
		assertThat(encoder.matches(actual.getPassword(), expected.getPassword())).isTrue();
		assertThat(actual.getName()).isEqualTo(expected.getName());
		assertThat(actual.getBirth()).isEqualTo(expected.getBirth());
		assertThat(actual.getTel()).isEqualTo(expected.getTel());
		assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
		assertThat(actual.getAuthority()).isEqualTo(expected.getAuthority());
	}

	@Test
	void test_selectAll() {
		List<Employee> expecteds = EmployeesManagementTestDataGenerator.getAllEmployees();
		List<Employee> actuals = mapper.selectAll();

		assertThat(actuals.size()).isEqualTo(expecteds.size());

		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(Employee::getEmployeeId).contains(expecteds.get(cnt).getEmployeeId());
			assertThat(actuals).extracting(Employee::getName).contains(expecteds.get(cnt).getName());
			assertThat(actuals).extracting(Employee::getBirth).contains(expecteds.get(cnt).getBirth());
			assertThat(actuals).extracting(Employee::getTel).contains(expecteds.get(cnt).getTel());
			assertThat(actuals).extracting(Employee::getAddress).contains(expecteds.get(cnt).getAddress());
			assertThat(actuals).extracting(Employee::getAuthority).contains(expecteds.get(cnt).getAuthority());
			assertThat(encoder.matches(actuals.get(cnt).getPassword(), expecteds.get(cnt).getPassword())).isTrue();
		}
	}

	@Test
	void test_selectAllIdAndName() {
		List<Employee> expecteds = EmployeesManagementTestDataGenerator.getAllEmployeeIdAndName();
		List<Employee> actuals = mapper.selectAllIdAndName();

		assertThat(actuals.size()).isEqualTo(expecteds.size());

		for (int cnt = 0; cnt < expecteds.size(); cnt++) {
			assertThat(actuals).extracting(Employee::getEmployeeId).contains(expecteds.get(cnt).getEmployeeId());
			assertThat(actuals).extracting(Employee::getName).contains(expecteds.get(cnt).getName());
		}
	}

	@Test
	void test_selectIdByName() {
		Integer id = mapper.selectIdByName("hoge");
		assertThat(id).isEqualTo(1001);
	}

	@Test
	void test_insert() {
		Employee foo = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.foo);
		mapper.insert(foo);
		String query="SELECT id,password, name, birth, tel, address, authority FROM employees WHERE id= ?;";
		Map<String, Object> confirm=jdbcTemplate.queryForMap(query,1004);
		assertThat(confirm.get("id")).isEqualTo(foo.getEmployeeId());
		assertThat(confirm.get("name")).isEqualTo(foo.getName());
		assertThat(confirm.get("password")).isEqualTo(foo.getPassword());
		assertThat(Date.valueOf(confirm.get("birth").toString())).isEqualTo(foo.getBirth().toString());
		assertThat(confirm.get("tel")).isEqualTo(foo.getTel());
		assertThat(confirm.get("address")).isEqualTo(foo.getAddress());
		assertThat(confirm.get("authority")).isEqualTo(foo.getAuthority().toString());
	}
	
	//異常系。nameにはUNIQUE制約があるので、一意制約違反。
	@Test
	void test_insert_invalid_because_name_is_duplicated() {
		Employee fuga =EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.fuga);
		fuga.setName("hoge");
		assertThrows(DataIntegrityViolationException.class, () -> mapper.insert(fuga));
	}

	//異常系。MySQLのデータ型の字数制限違反。いくつかあるが、今回はname VARCHAR(30)を検証
	@Test
	void test_insert_invalid_because_data_is_too_long() {
		Employee foo = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.foo);
		foo.setName("foofoofoofoofoofoofoofoofoofoofoo");//33文字
		assertThrows(DataIntegrityViolationException.class, () -> mapper.insert(foo));
	}

	@Test
	void test_update() {
		Employee fuga =EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.fuga);
		fuga.setName("hege");
		mapper.update(fuga);
		String query="SELECT id,password, name, birth, tel, address, authority FROM employees WHERE id= ?;";
		Map<String, Object> confirm=jdbcTemplate.queryForMap(query,1002);
		assertThat(confirm.get("name")).isEqualTo("hege");
	}
	
	//異常系。nameにはUNIQUE制約があるので、一意制約違反。
	@Test
	void test_update_invalid_because_name_is_duplicated() {
		Employee fuga =EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.fuga);
		fuga.setName("hoge");
		assertThrows(DataIntegrityViolationException.class, () -> mapper.update(fuga));
	}

//	異常系。MySQLのデータ型の字数制限違反。いくつかあるが、今回はaddress VARCHAR(50)を検証
	@Test
	void test_update_invalid_because_data_is_too_long() {
		Employee hoge = EmployeesManagementTestDataGenerator.getEmployee(EMPLOYEE.hoge);
		hoge.setAddress("福岡県古賀市天神1-1hogehogehogehogehogehogehogehogehogehoge");//51文字
		assertThrows(DataIntegrityViolationException.class, () -> mapper.update(hoge));
	}

	@Test
	@Sql(statements = "INSERT INTO employees (id,password, name, birth, tel, address, authority) VALUES"
			+ "  (1007,'p', 'hogehoge',   '1986-05-27', '030-1920-8394', '福岡県福岡市博多区', 'USER')")
	void test_deleteById() {
		String query="SELECT id,password, name, birth, tel, address, authority FROM employees WHERE id= ?;";
		Map<String, Object> before=jdbcTemplate.queryForMap(query,1007);
		assertThat(before).isNotNull();
		mapper.deleteById(1007);
		assertThrows(EmptyResultDataAccessException.class,()->jdbcTemplate.queryForMap(query,1007));
	}

	@Test
	//異常系。勤務履歴の存在する従業員を削除すると、DBのtime_recordテーブルの参照整合性違反によりエラーが発生する
	void test_deleteById_invalid_because_work_history_exists() {
		assertThrows(DataIntegrityViolationException.class, () -> mapper.deleteById(1001));
	}
}
