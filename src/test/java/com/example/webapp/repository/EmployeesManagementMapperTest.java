package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;

@MybatisTest
@Sql("EmployeesManagementMapperTest.sql")
public class EmployeesManagementMapperTest {

	@Autowired
	private EmployeesManagementMapper mapper;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	@Test
	void testSelectById() {
		Employee employee=mapper.selectById(1001);
		assertThat(employee.getEmployeeId()).isEqualTo(1001);
		assertThat(encoder.matches("yoshizuka01", employee.getPassword())).isTrue();
		assertThat(employee.getName()).isEqualTo("吉塚");
		assertThat(employee.getBirth()).isEqualTo(LocalDate.parse("1986-05-27"));
		assertThat(employee.getTel()).isEqualTo("030-1920-8394");
		assertThat(employee.getAddress()).isEqualTo("福岡県福岡市博多区吉塚本町13-28");
		assertThat(employee.getAuthority()).isEqualTo(Role.ADMIN);	
	}
	
	@Test
	void testSelectAll() {
		List<Employee> employees=mapper.selectAll();
		assertThat(employees.size()).isEqualTo(6);
	}
	
	@Test
	void testSelectIdByName() {
		Integer id=mapper.selectIdByName("吉塚");
		assertThat(id.equals(1001));
	}
	
	@Test
	void testInsert() {
		Employee newEmployee=new Employee(
				1007
				,encoder.encode("chihaya03")
				,"千早"
				,LocalDate.parse("1998-01-09")
				,"030-1974-4571"
				,"福岡県福岡市東区千早"
				,Role.USER
				,null
				,null
				,null);
		mapper.insert(newEmployee);
		Employee confirm=mapper.selectById(1007);
		assertThat(encoder.matches("chihaya03", confirm.getPassword())).isTrue();
		assertThat(confirm.getName()).isEqualTo("千早");
		assertThat(confirm.getBirth()).isEqualTo(LocalDate.parse("1998-01-09"));
		assertThat(confirm.getTel()).isEqualTo("030-1974-4571");
		assertThat(confirm.getAddress()).isEqualTo("福岡県福岡市東区千早");
		assertThat(confirm.getAuthority()).isEqualTo(Role.USER);	
	}
}
