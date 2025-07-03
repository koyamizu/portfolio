package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;

@MybatisTest
@Sql("ShiftManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ShiftManagementMapperTest {
	
	@Autowired
	private ShiftManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	LocalDate todayOfNextMonth=LocalDate.now().plusMonths(1);
	LocalDate firstDayOfNextMonth=LocalDate.of(todayOfNextMonth.getYear(),todayOfNextMonth.getMonthValue(),1);
	
	@Test
	void test_selectThreeMonthByTargetMonth() {
		List<FullCalendarEntity> actuals=mapper.selectThreeMonthByTargetMonth(5);
		var e=new Employee();
		e.setName("hogehoge");
		FullCalendarEntity confirm=new FullCalendarEntity(
				1,e,LocalDate.of(2025,4,1)
				,LocalTime.of(6,0,0),LocalTime.of(9,0,0)
				);
		assertThat(actuals.size()).isEqualTo(6);
		assertThat(actuals).contains(confirm);
	}
	
	@Test
	void test_selectRequestByEmployeeId() {

		List<FullCalendarEntity> actuals=mapper.selectRequestByEmployeeId(1001);
		assertThat(actuals.size()).isEqualTo(3);
		var e=new Employee();
		e.setEmployeeId(1001);
		FullCalendarEntity[] confirm= {
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e,firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(3,e,firstDayOfNextMonth.plusDays(27),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				};
		assertThat(actuals).contains(confirm);
	}
	
	@Test
	void test_selectOneMonthByTargetMonth() {
		List<FullCalendarEntity> actuals=mapper.selectOneMonthByTargetMonth(4);
		assertThat(actuals.size()).isEqualTo(3);
		var e=new Employee();
		e.setEmployeeId(1001);
		e.setName("hogehoge");
		FullCalendarEntity confirm=new FullCalendarEntity(
				1,e,LocalDate.of(2025,4,1)
				,LocalTime.of(6,0,0),LocalTime.of(9,0,0)
				);
		assertThat(actuals).contains(confirm);
	}
	
	@Test
	void test_selectAllRequests() {
		List<FullCalendarEntity> actuals=mapper.selectAllRequests();
		assertThat(actuals.size()).isEqualTo(5);
		var e=new Employee();
		e.setEmployeeId(1001);
		FullCalendarEntity confirm= 
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		assertThat(actuals.contains(confirm));
	}
	
	@Test
	void test_selectNotSubmit() {
		List<Employee> actuals=mapper.selectNotSubmit();
		var e=new Employee();
		e.setEmployeeId(1003);
		e.setName("piyopiyo");
		assertThat(actuals.size()).isEqualTo(1);
		assertThat(actuals).contains(e);
	}

	@Test
	void test_insertRequest() {
		var e=new Employee();
		e.setEmployeeId(1003);
		mapper.insertRequest(
				List.of(
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e,firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				)
		);
		String query="SELECT employee_id,date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,1003);
		assertThat(actuals.size()).isEqualTo(2);
		Map<String,Object> expected=
				Map.of("employee_id",1003,"date",Date.valueOf(firstDayOfNextMonth.toString())
		);
		assertThat(actuals).contains(expected);
	}
	
	@Test
	void test_insertRequest_invalid() {
		var e=new Employee();
		e.setEmployeeId(1003);
		List<FullCalendarEntity> testData=List.of(
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				);
		assertThrows(DataIntegrityViolationException.class, ()->mapper.insertRequest(testData)) ;
	}
	
	@Test
	void test_insertShift() {
		var e1001=new Employee();
		e1001.setEmployeeId(1001);
		var e1002=new Employee();
		e1002.setEmployeeId(1002);
		mapper.insertShift(
				List.of(
				new FullCalendarEntity(1,e1001,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e1001,firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(3,e1002,firstDayOfNextMonth.plusDays(28),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				)
		);
		String query="SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1";
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query);
		assertThat(actuals.size()).isEqualTo(3);
		List<Map<String,Object>> expected=List.of(
				Map.of("employee_id",1001,"date",Date.valueOf(firstDayOfNextMonth.toString()))
				,Map.of("employee_id",1002,"date",Date.valueOf(firstDayOfNextMonth.plusDays(28).toString()))
		);
		assertThat(actuals).containsAll(expected);
	}
	
	@Test
	void test_insertShift_invalid() {
		var e=new Employee();
		e.setEmployeeId(1003);
		List<FullCalendarEntity> testData=List.of(
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				);
		assertThrows(DataIntegrityViolationException.class, ()->mapper.insertShift(testData)) ;
	}
	
	@Test
	void test_deleteByEmployeeId() {
		String query="SELECT date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String,Object>> before=jdbcTemplate.queryForList(query,1001);
		assertThat(before).extracting(a->a.get("date")).contains(Date.valueOf(firstDayOfNextMonth.plusDays(27)));
		
		var e=new Employee();
		e.setEmployeeId(1001);
		List<FullCalendarEntity> newRequests=
				List.of(
				new FullCalendarEntity(1,e,firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e,firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				)
		;
		
		mapper.deleteOldRequestByEmployeeId(newRequests, 1001);
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,1001);
		assertThat(actuals).extracting(a->a.get("date")).doesNotContain(Date.valueOf(firstDayOfNextMonth.plusDays(27)));
	}
	
	@Test
	void test_deleteOldShiftByMonth() {
		String query="SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=?";
		List<Map<String,Object>> before=jdbcTemplate.queryForList(query,4);
		assertThat(before.size()).isEqualTo(3);
		
		var e1001=new Employee();
		e1001.setEmployeeId(1001);
		var e1002=new Employee();
		e1002.setEmployeeId(1002);
		List<FullCalendarEntity> newShifts=
				List.of(
				new FullCalendarEntity(1,e1001,LocalDate.of(2025,4,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				,new FullCalendarEntity(2,e1002,LocalDate.of(2025, 4, 30),LocalTime.of(6,0,0),LocalTime.of(9,0,0))
				)
		;
		
		mapper.deleteOldShiftByMonth(newShifts, 4);
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,4);
		assertThat(actuals.size()).isEqualTo(2);
		
	}
}
