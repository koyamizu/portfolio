package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;

import lombok.extern.slf4j.Slf4j;

@MybatisTest
@Sql("TimeRecorderMapperTest.sql")
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TimeRecorderMapperTest {

	@Autowired
	private TimeRecorderMapper mapper;

	@Test
	void test_selectByDate() {
		LocalDate date = LocalDate.of(2025, 5, 1);
		List<ShiftSchedule> actuals = mapper.selectByDate(date);
		assertThat(actuals).extracting(a -> a.getEmployee().getEmployeeId())
				.containsExactlyInAnyOrder(1001, 1002, 1003);
	}

	@Test
	void test_selectAMenberOfTodayEmployeesByEmployeeId() {
		List<ShiftSchedule> actuals = mapper.selectByDate(LocalDate.now());
		log.info(actuals.toString());
		//actualsはテストをした日の出勤者なので、selectAMemberOfTodayEmployeesByEmployeeIdがnullになることはない
		for (int count = 0; count < actuals.size(); count++) {
			Integer employeeId=actuals.get(count).getEmployee().getEmployeeId();
			Employee actual = mapper.selectAMenberOfTodayEmployeesByEmployeeId(employeeId);
			assertThat(actual).isNotNull();
		}
	}

	@Test
	void test_selectTodayTimeRecordByEmployeeId() {
		TimeRecord timeRecord=mapper.selectTodayTimeRecordByEmployeeId(1001);
		assertThat(timeRecord).isNull();
		mapper.insert(1001);
		TimeRecord actual=mapper.selectTodayTimeRecordByEmployeeId(1001);
		assertThat(actual).isNotNull();
	}

	//	mapper.selectTimeRecordByEmployeeId(Integer employeeId);
	//
	//	mapper.insert(Integer employeeId);
	//	
	//	mapper.update(Integer employeeId);
}
