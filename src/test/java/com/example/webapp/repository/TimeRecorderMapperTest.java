package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.TimeRecorderTestDataGenerator;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("TimeRecorderMapperTest.sql")
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TimeRecorderMapperTest {

	@Autowired
	private TimeRecorderMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Test
	void test_selectByDate() {
		List<ShiftSchedule> actuals = mapper.selectToday();
		List<ShiftSchedule> expecteds=TimeRecorderTestDataGenerator.getAllTimeRecorderTable();
		for(int cnt=0;cnt<actuals.size();cnt++) {
			assertThat(actuals).extracting(ShiftSchedule::getEmployee).contains(expecteds.get(cnt).getEmployee());
		}
	}

	@Test
	void test_selectTodayTimeRecordByEmployeeId() {
		TimeRecord actual=mapper.selectTodayTimeRecordByEmployeeId(1001);
		assertThat(actual).isNotNull();
	}

	@Test
	void test_insert() {
		mapper.insert(1002);
		String query="SELECT\n"
				+ "		  clock_in AS clockIn\n"
				+ "		FROM\n"
				+ "		  time_records\n"
				+ "		WHERE\n"
				+ "		  employee_id = ?\n"
				+ "		  AND date = CURRENT_DATE\n"
				+ "		;";
		Map<String,Object> actual=jdbcTemplate.queryForMap(query,1002);
		assertThat(actual).isNotNull();
	}
	
	@Test
	void test_update() {
		mapper.update(1001);
		String query="SELECT\n"
				+ "		  clock_in\n"
				+ "		  ,clock_out\n"
				+ "		FROM\n"
				+ "		  time_records\n"
				+ "		WHERE\n"
				+ "		  employee_id = ?\n"
				+ "		  AND date = CURRENT_DATE\n"
				+ "		;";
		Map<String,Object> actual=jdbcTemplate.queryForMap(query,1001);
		assertThat(actual.get("clock_in")).isNotNull();
		assertThat(actual.get("clock_out")).isNotNull();
	}
}
