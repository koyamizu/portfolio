package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("EmployeesManagementMapperTest.sql")
@Slf4j
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EmployeesManagementMapperTest {

	@Autowired
	private EmployeesManagementMapper mapper;
	
//	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	
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
		employees.stream().forEach(e->log.info(e.toString()));
	}
	
	@Test
	void testSelectAllIdAndName() {
		Map<Integer,String> employees=Map.of(
				1001,"吉塚"
				,1002,"古賀"
				,1003,"黒崎"
				,1004,"東郷"
				,1005,"小森江"
				,1006,"箱崎");
		List<Employee> actuals=mapper.selectAllIdAndName();
		for(int i=0;i<actuals.size();i++) {
			Integer key=actuals.get(i).getEmployeeId();
			String value=actuals.get(i).getName();
			assertThat(value).isEqualTo(employees.get(key));
		}
	}
	
	@Test
	void testSelectIdByName() {
		Integer id=mapper.selectIdByName("吉塚");
		assertThat(id.equals(1001));
	}
	
	@Test
	void testInsert() {
		Employee newEmployee=new Employee(
				null
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
	
	@Test
	void testUpdate() {
		Employee target=mapper.selectById(1002);
		log.info("名前変更前："+target.getName());
		target.setName("鹿部");
		mapper.update(target);
		Employee updated=mapper.selectById(1002);
		assertThat(updated.getName()).isEqualTo("鹿部");
	}
	
	@Test
	void testDelete() {
		Employee target=mapper.selectById(1001);
		log.info("{}はnull?：{}",1001,Objects.equals(target, null)?"true":"false");
		mapper.deleteById(1001);
		Employee result=mapper.selectById(1001);
		assertThat(result).isEqualTo(null);
	}
}
