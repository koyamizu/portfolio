package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.common.EmployeeTestData;
import com.example.webapp.entity.Employee;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
//shift_scheduleと外部制約がある関係で、既存の従業員のdeleteができないので
//テスト用のデータを個別に挿入
@Sql("EmployeesManagementMapperTest.sql")
@Slf4j
public class EmployeesManagementMapperTest {

	@Autowired
	private EmployeesManagementMapper mapper;
	
	private BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
	
	private EmployeeTestData data=new EmployeeTestData();
	
	@Test
	void testSelectById() {
		Employee yoshizuka=data.getYoshizuka();
		Employee actual=mapper.selectById(1001);
		assertThat(actual.getEmployeeId()).isEqualTo(yoshizuka.getEmployeeId());
		assertThat(encoder.matches(yoshizuka.getPassword(), actual.getPassword())).isTrue();
		assertThat(actual.getName()).isEqualTo(yoshizuka.getName());
		assertThat(actual.getBirth()).isEqualTo(yoshizuka.getBirth());
		assertThat(actual.getTel()).isEqualTo(yoshizuka.getTel());
		assertThat(actual.getAddress()).isEqualTo(yoshizuka.getAddress());
		assertThat(actual.getAuthority()).isEqualTo(yoshizuka.getAuthority());	
	}
	
	@Test
	void testSelectAll() {
		List<Employee> actuals=mapper.selectAll();
		
		assertThat(actuals.size()).isEqualTo(6);
		
		Employee yoshizuka=data.getYoshizuka();
		
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
		List<Employee> actuals=mapper.selectAllIdAndName();
		
		assertThat(actuals.size()).isEqualTo(6);
		
		Employee yoshizuka=data.getYoshizuka();
		
		assertThat(actuals).extracting(Employee::getEmployeeId).contains(yoshizuka.getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(yoshizuka.getName());
	}
	
	@Test
	void testSelectIdByName() {
		Integer id=mapper.selectIdByName("吉塚");
		assertThat(id).isEqualTo(1001);
	}
	
	@Test
	void testInsert() {
		Employee chihaya=data.getChihaya();
		mapper.insert(chihaya);
		Employee confirm=mapper.selectById(1007);
		assertThat(confirm.getPassword()).isEqualTo(chihaya.getPassword());
		assertThat(confirm.getName()).isEqualTo(chihaya.getName());
		assertThat(confirm.getBirth()).isEqualTo(chihaya.getBirth());
		assertThat(confirm.getTel()).isEqualTo(chihaya.getTel());
		assertThat(confirm.getAddress()).isEqualTo(chihaya.getAddress());
		assertThat(confirm.getAuthority()).isEqualTo(chihaya.getAuthority());
	}
	
	@Test
	void testUpdate() {
		Employee koga=mapper.selectById(1002);
		log.info("名前変更前："+koga.getName());
		koga.setName("鹿部");
		mapper.update(koga);
		Employee exKoga=mapper.selectById(1002);
		assertThat(exKoga.getName()).isEqualTo("鹿部");
	}
	
	@Test
	void testDelete() {
		assertThat(mapper.selectById(1001)).isNotNull();
		mapper.deleteById(1001);
		assertThat(mapper.selectById(1001)).isNull();
	}
}
