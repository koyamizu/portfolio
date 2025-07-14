package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.WorkHistoryManagementTestDataGenerator;

@MybatisTest
@Sql("WorkHistoryManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class WorkHistoryManagementMapperTest {

	@Autowired
	private WorkHistoryManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	void test_selectAllByMonth() {
		List<TimeRecord> expected=WorkHistoryManagementTestDataGenerator.getAllWorkHistory();
		List<TimeRecord> actuals=mapper.selectAllByMonth(4);
		assertThat(actuals.size()).isEqualTo(expected.size());
		for(int cnt=0;cnt<actuals.size();cnt++) {
			assertThat(actuals).extracting(TimeRecord::getEmployeeId).contains(expected.get(cnt).getEmployeeId());
			assertThat(actuals).extracting(TimeRecord::getEmployeeName).contains(expected.get(cnt).getEmployeeName());
			assertThat(actuals).extracting(TimeRecord::getDate).contains(expected.get(cnt).getDate());
			assertThat(actuals).extracting(a->a.getAbsenceReason().getName()).contains(expected.get(cnt).getAbsenceReason().getName());
		}
	}
	
	@Test
	void test_selectByEmployeeIdAndMonth() {
		List<TimeRecord> actuals=mapper.selectByEmployeeIdAndMonth(1002, 5);
		List<TimeRecord> expected=WorkHistoryManagementTestDataGenerator.getFugaWorkHistoryOfMay();
		assertThat(actuals.size()).isEqualTo(expected.size());
		for(int cnt=0;cnt<actuals.size();cnt++) {
			assertThat(actuals).extracting(TimeRecord::getEmployeeId).contains(expected.get(cnt).getEmployeeId());
			assertThat(actuals).extracting(TimeRecord::getEmployeeName).contains(expected.get(cnt).getEmployeeName());
			assertThat(actuals).extracting(TimeRecord::getDate).contains(expected.get(cnt).getDate());
			assertThat(actuals).extracting(a->a.getAbsenceReason().getName()).contains(expected.get(cnt).getAbsenceReason().getName());
		}
	}

	@Test
	void test_selectEmployeeByMonth() {
		List<Employee> expecteds=WorkHistoryManagementTestDataGenerator.getEmployeeIdAndNameWhoWorkedInMay();
		List<Employee> actuals=mapper.selectEmployeeByMonth(5);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for(int cnt=0;cnt<actuals.size();cnt++) {
			assertThat(actuals).extracting(Employee::getEmployeeId).contains(expecteds.get(cnt).getEmployeeId());
			assertThat(actuals).extracting(Employee::getName).contains(expecteds.get(cnt).getName());
		}
	}
	
	@Test
	void test_selectByEmployeeIdAndDate() {
		TimeRecord actual=mapper.selectByEmployeeIdAndDate(1002, LocalDate.of(2025, 5, 11));
		TimeRecord expected=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 11);
		assertThat(actual.getDate()).isEqualTo(expected.getDate());
		assertThat(actual.getClockIn()).isEqualTo(expected.getClockIn());
		assertThat(actual.getClockOut()).isEqualTo(expected.getClockOut());
		assertThat(actual.getEmployeeId()).isEqualTo(expected.getEmployeeId());
		assertThat(actual.getEmployeeName()).isEqualTo(expected.getEmployeeName());
	}
	
	@Test
	void test_update() {
		TimeRecord updatedHistory=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 1);
		updatedHistory.setClockIn(LocalTime.of(5,50,01));
		updatedHistory.setClockOut(LocalTime.of(9,10,01));
		mapper.update(updatedHistory);
		String query="SELECT\n"
				+ "		  t.date\n"
				+ "		  ,t.clock_in\n"
				+ "		  ,t.clock_out\n"
				+ "		  ,t.employee_id\n"
				+ "		  ,e.name AS employee_name\n"
				+ "		FROM\n"
				+ "		  time_records AS t\n"
				+ "		  INNER JOIN employees AS e\n"
				+ "	 	  ON t.employee_id = e.id\n"
				+ "		WHERE\n"
				+ "		  t.date=?\n"
				+ "		  AND t.employee_id=?\n"
				+ "		;";
		Map<String,Object> confirm=jdbcTemplate.queryForMap(query,LocalDate.of(2025, 5, 1),1002);
		assertThat(confirm.get("clock_in").toString()).isEqualTo(updatedHistory.getClockIn().toString());
		assertThat(confirm.get("clock_out").toString()).isEqualTo(updatedHistory.getClockOut().toString());
	}
		
	@Test
	@Transactional
	void test_deleteAllShiftRequests() {
		String query="SELECT employee_id FROM time_records WHERE employee_id=?";
		List<Map<String, Object>> before=jdbcTemplate.queryForList(query,1002);
		assertThat(before).isNotEmpty();
		mapper.deleteAllTimeRecords(1002);
		List<Map<String,Object>> after=jdbcTemplate.queryForList(query,1002);
		assertThat(after).isEmpty();
	}
}
