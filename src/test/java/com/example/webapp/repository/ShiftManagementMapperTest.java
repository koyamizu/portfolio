package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.FullCalendarEntity;

@MybatisTest
@Sql("ShiftManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ShiftManagementMapperTest {
	
	@Autowired
	private ShiftManagementMapper mapper;
	
	@Test
	void test_selectThreeMonthByTargetMonth() {
		List<FullCalendarEntity> actuals=mapper.selectThreeMonthByTargetMonth(5);
		assertThat(actuals.size()).isEqualTo(6);
	}
//	List<FullCalendarEntity> selectThreeMonthByTargetMonth(Integer targetMonth);
//
//	List<FullCalendarEntity> selectByEmployeeId(Integer employeeId);
//	
//	List<ShiftSchedule> selectAllAfterTodayByEmployeeId(Integer employeeId);
//
//	List<FullCalendarEntity> selectOneMonthByTargetMonth(Integer targetMonth);
//
//	List<FullCalendarEntity> selectAll();
//
//	List<Employee> selectNotSubmit();
//
//	void insertRequest(List<FullCalendarEntity> requests);
//
//	void insertShift(List<FullCalendarEntity> newShifts);
//
//	void deleteByEmployeeId(List<FullCalendarEntity> requests, Integer employeeId);
//
//	void deleteByMonth(List<FullCalendarEntity> newShifts,Integer targetMonth);
}
