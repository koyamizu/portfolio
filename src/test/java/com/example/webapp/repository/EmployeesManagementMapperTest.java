package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.test_data.EmployeeTestData;
import com.example.webapp.test_data.employee.EMPLOYEE;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("EmployeesManagementMapperTest.sql")
@Slf4j
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class EmployeesManagementMapperTest {

	@Autowired
	private EmployeesManagementMapper mapper;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@Test
	void test_selectById() {
		Employee expected=EmployeeTestData.getEmployee(EMPLOYEE.hoge);
		Employee actual=mapper.selectById(1001);
		assertThat(actual.getEmployeeId()).isEqualTo(expected.getEmployeeId());
		assertThat(encoder.matches(actual.getPassword(),expected.getPassword())).isTrue();
		assertThat(actual.getName()).isEqualTo(expected.getName());
		assertThat(actual.getBirth()).isEqualTo(expected.getBirth());
		assertThat(actual.getTel()).isEqualTo(expected.getTel());
		assertThat(actual.getAddress()).isEqualTo(expected.getAddress());
		assertThat(actual.getAuthority()).isEqualTo(expected.getAuthority());	
	}
	
	@Test
	void test_selectAll() {
		List<Employee> expecteds=EmployeeTestData.getAllEmployees();
		List<Employee> actuals=mapper.selectAll();
		
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		
		assertThat(actuals).extracting(Employee::getEmployeeId).contains(expecteds.get(0).getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(expecteds.get(0).getName());
		assertThat(actuals).extracting(Employee::getBirth).contains(expecteds.get(0).getBirth());
		assertThat(actuals).extracting(Employee::getTel).contains(expecteds.get(0).getTel());
		assertThat(actuals).extracting(Employee::getAddress).contains(expecteds.get(0).getAddress());
		assertThat(actuals).extracting(Employee::getAuthority).contains(expecteds.get(0).getAuthority());
		assertThat(encoder.matches(actuals.get(0).getPassword(),expecteds.get(0).getPassword())).isTrue();		
	}
	
	@Test
	void test_selectAllIdAndName() {
		List<Employee> expecteds=EmployeeTestData.getAllEmployeeIdAndName();
		List<Employee> actuals=mapper.selectAllIdAndName();
		
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		
		assertThat(actuals).extracting(Employee::getEmployeeId).contains(expecteds.get(0).getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(expecteds.get(0).getName());
	}
	
	@Test
	void test_selectIdByName() {
		Integer id=mapper.selectIdByName("hoge");
		assertThat(id).isEqualTo(1001);
	}
	
	@Test
	void test_insert() {
		Employee foo=EmployeeTestData.getEmployee(EMPLOYEE.foo);
		mapper.insert(foo);
		Employee confirm=mapper.selectById(foo.getEmployeeId());
		assertThat(confirm.getPassword()).isEqualTo(foo.getPassword());
		assertThat(confirm.getName()).isEqualTo(foo.getName());
		assertThat(confirm.getBirth()).isEqualTo(foo.getBirth());
		assertThat(confirm.getTel()).isEqualTo(foo.getTel());
		assertThat(confirm.getAddress()).isEqualTo(foo.getAddress());
		assertThat(confirm.getAuthority()).isEqualTo(foo.getAuthority());
	}
	
	//異常系。MySQLのデータ型の字数制限違反。いくつかあるが、今回はname VARCHAR(30)を検証
	@Test
	void test_insert_too_long_data(){
		Employee foo=EmployeeTestData.getEmployee(EMPLOYEE.foo);
		foo.setName("foofoofoofoofoofoofoofoofoofoofoo");//33文字
		assertThrows(DataIntegrityViolationException.class,()->mapper.insert(foo));
	}
	
	@Test
	void test_update() {
		Employee fuga=mapper.selectById(1002);
		log.info("名前変更前："+fuga.getName());
		fuga.setName("hege");
		mapper.update(fuga);
		Employee actual=mapper.selectById(1002);
		assertThat(actual.getName()).isEqualTo("hege");
	}
	
	//異常系。MySQLのデータ型の字数制限違反。いくつかあるが、今回はaddress VARCHAR(50)を検証
	@Test
	void test_update_too_long_data() {
		Employee hoge=mapper.selectById(1001);
		hoge.setAddress("福岡県古賀市天神1-1hogehogehogehogehogehogehogehogehogehoge");//51文字
		assertThrows(DataIntegrityViolationException.class,()->mapper.update(hoge));
	}
	
	@Test
	@Sql(statements="INSERT INTO employees (id,password, name, birth, tel, address, authority) VALUES"
			+ "  (1007,'p', 'hogehoge',   '1986-05-27', '030-1920-8394', '福岡県福岡市博多区', 'USER')")
	void test_deleteById() {
		assertThat(mapper.selectById(1007)).isNotNull();
		mapper.deleteById(1007);
		assertThat(mapper.selectById(1007)).isNull();
	}
	
	@Test
	//異常系。勤務履歴の存在する従業員を削除すると、DBのtime_recordテーブルの参照整合性違反によりエラーが発生する
	void test_deleteById_invalid_because_work_history_exists() {
		assertThrows(DataIntegrityViolationException.class,()->mapper.deleteById(1001));
	}
}
