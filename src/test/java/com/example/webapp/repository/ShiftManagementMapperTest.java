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
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.ShiftManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("ShiftManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class ShiftManagementMapperTest {

	@Autowired
	private ShiftManagementMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void test_selectThreeMonthByTargetMonth() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAllSchedules();
		List<FullCalendarEntity> actuals = mapper.selectThreeMonthByTargetMonth(5);

		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(FullCalendarEntity::getEmployee).contains(expecteds.get(cnt).getEmployee());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledStart)
					.contains(expecteds.get(cnt).getScheduledStart());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledEnd)
					.contains(expecteds.get(cnt).getScheduledEnd());
			assertThat(actuals).extracting(FullCalendarEntity::getStart).contains(expecteds.get(cnt).getStart());
		}
	}

	//TODO スプレッドシートに記述→「オートインクリメントで作成される値（XXidなど）は、テストデータ生成の柔軟性を高めるためテストデータに含めていない」
	@Test
	void test_selectOneMonthByTargetMonth() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAprilSchedules();
		List<FullCalendarEntity> actuals = mapper.selectOneMonthByTargetMonth(4);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(FullCalendarEntity::getEmployee).contains(expecteds.get(cnt).getEmployee());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledStart)
					.contains(expecteds.get(cnt).getScheduledStart());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledEnd)
					.contains(expecteds.get(cnt).getScheduledEnd());
			assertThat(actuals).extracting(FullCalendarEntity::getStart).contains(expecteds.get(cnt).getStart());
		}
	}

	@Test
	void test_selectRequestByEmployeeId() {
		//		selectRequestByEmployeeIdはnameは抽出しない。FullCalendarの表示に不要なため。
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAllHogeRequests();
		List<FullCalendarEntity> actuals = mapper.selectRequestByEmployeeId(1001);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledStart)
					.contains(expecteds.get(cnt).getScheduledStart());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledEnd)
					.contains(expecteds.get(cnt).getScheduledEnd());
			assertThat(actuals).extracting(FullCalendarEntity::getStart).contains(expecteds.get(cnt).getStart());
		}
	}

	@Test
	void test_selectAllRequests() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAllRequests();
		List<FullCalendarEntity> actuals = mapper.selectAllRequests();
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(FullCalendarEntity::getEmployee).contains(expecteds.get(cnt).getEmployee());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledStart)
					.contains(expecteds.get(cnt).getScheduledStart());
			assertThat(actuals).extracting(FullCalendarEntity::getScheduledEnd)
					.contains(expecteds.get(cnt).getScheduledEnd());
			assertThat(actuals).extracting(FullCalendarEntity::getStart).contains(expecteds.get(cnt).getStart());
		}
	}

	@Test
	void test_selectNotSubmit() {
		Employee piyo = EmployeesManagementTestDataGenerator.getEmployeeIdAndName(EMPLOYEE.piyo);
		List<Employee> actuals = mapper.selectNotSubmit();
		assertThat(actuals.size()).isEqualTo(1);
		assertThat(actuals).extracting(Employee::getEmployeeId).contains(piyo.getEmployeeId());
		assertThat(actuals).extracting(Employee::getName).contains(piyo.getName());
	}

	@Test
	void test_insertRequest() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAllPiyoRequests();
		mapper.insertRequest(expecteds);
		String query = "SELECT employee_id,date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String, Object>> actuals = jdbcTemplate.queryForList(query, 1003);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(a->a.get("employee_id"))
					.contains(expecteds.get(cnt).getEmployee().getEmployeeId());
			assertThat(actuals).extracting(a->a.get("date")).contains(Date.valueOf(expecteds.get(cnt).getStart()));
		}
	}

	@Test
	void test_insertRequest_invalid_because_working_date_are_duplicated() {
		List<FullCalendarEntity> insertData = ShiftManagementTestDataGenerator.getDuplicatedPiyoRequests();
		assertThrows(DataIntegrityViolationException.class, () -> mapper.insertRequest(insertData));
	}

	@Test
	void test_insertShift() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getNextMonthScheduleToInsert();
		mapper.insertShift(expecteds);
		String query = "SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1";
		List<Map<String, Object>> actuals = jdbcTemplate.queryForList(query);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(a -> a.get("employee_id"))
					.contains(expecteds.get(cnt).getEmployee().getEmployeeId());
			assertThat(actuals).extracting(a -> a.get("date"))
					.contains(Date.valueOf(expecteds.get(cnt).getStart()));
		}
	}

	@Test
	void test_insertShift_invalid() {
		List<FullCalendarEntity> insertData = ShiftManagementTestDataGenerator
				.getDuplicatedHogeNextMonthScheduleToInsert();
		assertThrows(DataIntegrityViolationException.class, () -> mapper.insertShift(insertData));
	}

	@Test
	void test_deleteOldReqeustByEmployeeId() {
		String query = "SELECT date "
				+ "FROM shift_requests AS r INNER JOIN employees AS e ON r.employee_id=e.id "
				+ "WHERE MONTH(date)=MONTH(CURRENT_DATE)+1 AND employee_id=?";
		List<Map<String, Object>> before = jdbcTemplate.queryForList(query, 1001);
		//更新版のシフトには存在しない勤務日
		assertThat(before).extracting(a -> a.get("date"))
				.contains(Date.valueOf(ShiftManagementTestDataGenerator.hogeRequestDateStringToDelete()));

		List<FullCalendarEntity> latestVersionRequests = ShiftManagementTestDataGenerator
				.getLatestVersionHogeRequests();

		mapper.deleteOldRequestByEmployeeId(latestVersionRequests, 1001);
		List<Map<String, Object>> actuals = jdbcTemplate.queryForList(query, 1001);
		assertThat(actuals.size()).isEqualTo(latestVersionRequests.size());
	}

	@Test
	void test_deleteOldShiftByMonth() {
		List<FullCalendarEntity> expecteds = ShiftManagementTestDataGenerator.getAprilSchedules();
		String query = "SELECT employee_id,date "
				+ "FROM shift_schedules AS s INNER JOIN employees AS e ON s.employee_id=e.id "
				+ "WHERE MONTH(date)=?";
		List<Map<String, Object>> before = jdbcTemplate.queryForList(query, 4);
		assertThat(before.size()).isEqualTo(expecteds.size());

		List<FullCalendarEntity> updatedSchedules = ShiftManagementTestDataGenerator.getUpdatedAprilSchedules();

		mapper.deleteOldShiftByMonth(updatedSchedules, 4);
		List<Map<String, Object>> actuals = jdbcTemplate.queryForList(query, 4);

		for (int cnt = 0; cnt < actuals.size(); cnt++) {
			assertThat(actuals).extracting(a -> a.get("employee_id"))
					.contains(updatedSchedules.get(cnt).getEmployee().getEmployeeId());
			assertThat(actuals).extracting(a -> a.get("date").toString())
					.contains(updatedSchedules.get(cnt).getStart().toString());
		}
	}

	@Test
	@Transactional
	void test_deleteAllShiftSchedules() {
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 0;");
		String query = "SELECT employee_id FROM shift_schedules WHERE employee_id=?";
		List<Map<String, Object>> before = jdbcTemplate.queryForList(query, 1001);
		assertThat(before).isNotEmpty();
		mapper.deleteAllShiftSchedulesByEmployeeId(1001);
		List<Map<String, Object>> after = jdbcTemplate.queryForList(query, 1001);
		assertThat(after).isEmpty();
		jdbcTemplate.execute("SET FOREIGN_KEY_CHECKS = 1;");
	}

	@Test
	@Transactional
	void test_deleteAllShiftSchedules_invalid_because_of_foreign_key_constraint() {
		String query = "SELECT employee_id FROM shift_schedules WHERE employee_id=?";
		List<Map<String, Object>> before = jdbcTemplate.queryForList(query, 1001);
		assertThat(before).isNotEmpty();
		assertThrows(DataIntegrityViolationException.class, () -> mapper.deleteAllShiftSchedulesByEmployeeId(1001));
	}

	@Test
	@Transactional
	void test_deleteAllShiftRequests() {
		String query = "SELECT employee_id FROM shift_requests WHERE employee_id=?";
		List<Map<String, Object>> before = jdbcTemplate.queryForList(query, 1001);
		assertThat(before).isNotEmpty();
		mapper.deleteAllShiftRequestsByEmployeeId(1001);
		List<Map<String, Object>> after = jdbcTemplate.queryForList(query, 1001);
		assertThat(after).isEmpty();
	}
}
