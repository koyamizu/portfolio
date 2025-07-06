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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.test_data.EmployeeTestData;
import com.example.webapp.test_data.ShiftManagementTestData;

@MybatisTest
@Sql("ShiftManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class ShiftManagementMapperTest {
	
	@Autowired
	private ShiftManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	private ShiftManagementTestData data=new ShiftManagementTestData();
	
	@Test
	void test_selectThreeMonthByTargetMonth() {
		List<FullCalendarEntity> expecteds=data.getAllSchedules();
		List<FullCalendarEntity> actuals=mapper.selectThreeMonthByTargetMonth(5);

		FullCalendarEntity confirm=data.getHogeSchedule0401();
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).contains(confirm);
	}
	@Test
	void test_selectOneMonthByTargetMonth() {
		List<FullCalendarEntity> expecteds=data.getAprilSchedules();
		List<FullCalendarEntity> actuals=mapper.selectOneMonthByTargetMonth(4);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).containsExactlyInAnyOrderElementsOf(expecteds);
	}
	
	@Test
	void test_selectRequestByEmployeeId() {
//		selectRequestByEmployeeIdはnameは抽出しない。FullCalendarの表示に不要なため。
//		getAllHogeRequestsOnlyEmployeeId内でexpectedsのnameフィールドをnullにしている
		List<FullCalendarEntity> expecteds=data.getAllHogeRequestsOnlyEmployeeId();
		List<FullCalendarEntity> actuals=mapper.selectRequestByEmployeeId(1001);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).containsAll(expecteds);
	}
	
	
	@Test
	void test_selectAllRequests() {
		List<FullCalendarEntity> expecteds=data.getAllRequests();
		List<FullCalendarEntity> actuals=mapper.selectAllRequests();
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).containsExactlyInAnyOrderElementsOf(expecteds);
	}
	
	@Test
	void test_selectNotSubmit() {
		var factory=new EmployeeTestData();
		Employee piyo=factory.getExistingEmployeePiyo();
		List<Employee> actuals=mapper.selectNotSubmit();
		assertThat(actuals.size()).isEqualTo(1);
		assertThat(actuals).extracting(Employee::getEmployeeId).contains(piyo.getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(piyo.getName());
	}

	@Test
	void test_insertRequest() {
		List<FullCalendarEntity> expecteds=data.getPiyoRequests();
		mapper.insertRequest(expecteds);
		String query="SELECT employee_id,date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,1003);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		Map<String,Object> expected=
				Map.of("employee_id",1003,"date",Date.valueOf(expecteds.get(0).getStart().toString())
		);
		assertThat(actuals).contains(expected);
	}
	
	@Test
	void test_insertRequest_invalid() {
		List<FullCalendarEntity> insertData=data.getDuplicatedPiyoRequests();
		assertThrows(DataIntegrityViolationException.class, ()->mapper.insertRequest(insertData)) ;
	}
	
	@Test
	void test_insertShift() {
		List<FullCalendarEntity> expecteds=data.getNextMonthScheduleToInsert();
		mapper.insertShift(expecteds);
		String query="SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1";
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query);
		assertThat(actuals.size()).isEqualTo(3);
		List<Map<String,Object>> expected=List.of(
				Map.of("employee_id",expecteds.get(0).getEmployee()
						,"date",Date.valueOf(expecteds.get(0).getStart().toString()))
				,Map.of("employee_id",expecteds.get(2)
						,"date",Date.valueOf(expecteds.get(2).getStart().toString()))
		);
	}
	
	@Test
	void test_insertShift_invalid() {
		List<FullCalendarEntity> insertData=data.getDuplicatedHogeScheduleToInsert();
		assertThrows(DataIntegrityViolationException.class, ()->mapper.insertShift(insertData)) ;
	}
	
	@Test
	void test_deleteOldReqeustByEmployeeId() {
		String query="SELECT date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String,Object>> before=jdbcTemplate.queryForList(query,1001);
		assertThat(before).extracting(a->a.get("date")).contains(Date.valueOf(data.hogeRequestDateToDelete()));
		
		List<FullCalendarEntity> updatedRequests=data.getUpdatedHogeRequests();
		
		mapper.deleteOldRequestByEmployeeId(updatedRequests, 1001);
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,1001);
		assertThat(actuals.size()).isEqualTo(updatedRequests.size());
	}
	
	@Test
	void test_deleteOldShiftByMonth() {
		List<FullCalendarEntity> expecteds=data.getAprilSchedules();
		String query="SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=?";
		List<Map<String,Object>> before=jdbcTemplate.queryForList(query,4);
		assertThat(before.size()).isEqualTo(expecteds.size());
		
		List<FullCalendarEntity> updatedSchedules=data.getUpdatedAprilSchedules();
		
		mapper.deleteOldShiftByMonth(updatedSchedules, 4);
		List<Map<String,Object>> actuals=jdbcTemplate.queryForList(query,4);
		assertThat(actuals.size()).isEqualTo(updatedSchedules.size());
		
	}
	
	@Test
	@Transactional
	@Sql(scripts="ShiftManagementMapperTest.sql",statements="SET FOREIGN_KEY_CHECKS = 0;")
	void test_deleteAllShiftSchedules() {
		String query="SELECT employee_id FROM shift_schedules WHERE employee_id=?";
		List<Map<String,Object>> before=jdbcTemplate.queryForList(query,1001);
		assertThat(before).isNotEmpty();
		mapper.deleteAllShiftSchedules(1001);
		List<Map<String,Object>> after=jdbcTemplate.queryForList(query,1001);
		assertThat(after).isEmpty();
	}
	
	@Test
	@Transactional
	@Sql(scripts="ShiftManagementMapperTest.sql",statements="SET FOREIGN_KEY_CHECKS = 0;")
	void test_deleteAllShiftRequests() {
		String query="SELECT employee_id FROM shift_requests WHERE employee_id=?";
		List<Map<String, Object>> before=jdbcTemplate.queryForList(query,1001);
		assertThat(before).isNotEmpty();
		mapper.deleteAllShiftRequests(1001);
		List<Map<String,Object>> after=jdbcTemplate.queryForList(query,1001);
		assertThat(after).isEmpty();
	}
}
