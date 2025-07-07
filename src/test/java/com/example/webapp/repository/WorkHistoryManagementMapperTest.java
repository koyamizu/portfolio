package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.WorkHistoryManagementTestData;

@MybatisTest
@Sql("WorkHistoryManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class WorkHistoryManagementMapperTest {

	@Autowired
	private WorkHistoryManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	private WorkHistoryManagementTestData data;
	
	@BeforeEach
	void set() {
		data=new WorkHistoryManagementTestData();
	}
	
	@Test
	void test_selectAllToDateByMonth() {
		List<TimeRecord> actuals=mapper.selectAllByMonth(4);
		assertThat(actuals.size()).isEqualTo(3);
		TimeRecord absenceAndApprove=data.getEmployeeId_1001_Date_0401_AbsenceReason_1_Approved();
		TimeRecord absenceAndNotApprove=data.getEmployeeId_1002_Date_0401_AbsenceReason_1_NotApproved();
		TimeRecord absenceAndApproveNull=data.getEmployeeId_1003_Date_0401_AbsenceReason_2_IsApprovedEqualsNull();
		assertThat(actuals).contains(absenceAndApprove);
		assertThat(actuals).contains(absenceAndNotApprove);
		assertThat(actuals).contains(absenceAndApproveNull);
	}
	
	@Test
	void test_selectToDateByEmployeeIdAndMonth() {
		List<TimeRecord> actuals=mapper.selectByEmployeeIdAndMonth(1002, 5);
		TimeRecord expected=data.getEmployeeId_1002_Date_0501_worked();
		assertThat(actuals.size()).isEqualTo(2);
		assertThat(actuals).contains(expected);
	}

	@Test
	void test_selectEmployeeByMonth() {
		List<Employee> employees=mapper.selectEmployeeByMonth(4);
		assertThat(employees.size()).isEqualTo(3);
		assertThat(employees).extracting(e->e.getEmployeeId())
		.containsExactlyInAnyOrderElementsOf(List.of(1001,1002,1003));
	}
	
	@Test
	void test_selectByEmployeeIdAndDate() {
		TimeRecord actual=mapper.selectByEmployeeIdAndDate(1002, LocalDate.of(2025, 5, 11));
		TimeRecord expected=data.getEmployeeId_1002_Date_0511_worked();
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	void test_update() {
		TimeRecord updatedHistory=data.getEmployeeId_1002_Date_0501_worked();
		updatedHistory.setClockIn(LocalTime.of(5,50,00));
		updatedHistory.setClockOut(LocalTime.of(9,10,00));
		mapper.update(updatedHistory);
		String query=data.selectByEmployeeIdAndDate();
		Map<String,Object> confirm=jdbcTemplate.queryForMap(query,LocalDate.of(2025, 5, 1),1002);
		assertThat(confirm).isNotEqualTo(updatedHistory);
	}
	
	@Test
	@Transactional
	@Sql(scripts="WorkHistoryManagementMapperTest.sql",statements="SET FOREIGN_KEY_CHECKS = 0;")
	void test_deleteAllShiftRequests() {
		String query="SELECT employee_id FROM time_records WHERE employee_id=?";
		List<Map<String, Object>> before=jdbcTemplate.queryForList(query,1002);
		assertThat(before).isNotEmpty();
		mapper.deleteAllTimeRecords(1002);
		List<Map<String,Object>> after=jdbcTemplate.queryForList(query,1002);
		assertThat(after).isEmpty();
	}
}
