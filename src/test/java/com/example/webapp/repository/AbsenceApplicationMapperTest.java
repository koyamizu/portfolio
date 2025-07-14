package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.test_data.AbsenceApplicationTestDataGenerator;
import com.example.webapp.test_data.AbsenceReasonTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

@MybatisTest
@Sql("AbsenceApplicationMapperTest.sql")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AbsenceApplicationMapperTest {
	
	@Autowired
	private AbsenceApplicationMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	void test_selectAll() {
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getAllApplication();
		 List<AbsenceApplication> actuals=mapper.selectAll();
		 assertThat(actuals.size()).isEqualTo(expecteds.size());
		 for(int cnt=0;cnt<actuals.size();cnt++) {
			 assertThat(actuals).extracting(a->a.getEmployee().getName()).contains(expecteds.get(cnt).getEmployee().getName());
			 assertThat(actuals).extracting(a->a.getAbsenceReason().getName()).contains(expecteds.get(cnt).getAbsenceReason().getName());
			 assertThat(actuals).extracting(AbsenceApplication::getDetail).contains(expecteds.get(cnt).getDetail());
		 }
	}
	
	@Test
	void test_selectAllByEmployeeId() {		
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getAllHogeApplication();
		List<AbsenceApplication> actuals=mapper.selectAllByEmployeeId(1001);
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		for(int cnt=0;cnt<actuals.size();cnt++) {
		assertThat(actuals).extracting(a->a.getShiftSchedule().getDate()).contains(expecteds.get(cnt).getShiftSchedule().getDate());
		assertThat(actuals).extracting(a->a.getEmployee().getName()).contains(expecteds.get(cnt).getEmployee().getName());
		assertThat(actuals).extracting(a->a.getAbsenceReason().getName()).contains(expecteds.get(cnt).getAbsenceReason().getName());
		assertThat(actuals).extracting(AbsenceApplication::getDetail).contains(expecteds.get(cnt).getDetail());
		assertThat(actuals).extracting(AbsenceApplication::getIsApprove).contains(expecteds.get(cnt).getIsApprove());
		}
	}
	
	/*マッパーメソッドではシフトの日付を抽出していないので、getShiftSchedule().getDate()をするとnullになる。
	 * 		<select id="selectTodayAndIsApproveEqualsNull" resultMap="AbsenceApplicationWithShiftScheduleAndEmployeeAndAbsenceResult">
			SELECT
			  aa.id
			  ,e.name AS employee_name
			  ,ar.name AS reason_name
			FROM（以下略*/
	@Test
	void test_selectToday() {
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getTodayApplication();
		List<AbsenceApplication> actuals=mapper.selectTodayAndIsApproveEqualsNull();
		for(int cnt=0;cnt<actuals.size();cnt++) {
//		assertThat(actuals).extracting(a->a.getShiftSchedule().getDate()).contains(expecteds.get(cnt).getShiftSchedule().getDate());
		assertThat(actuals).extracting(a->a.getEmployee().getName()).contains(expecteds.get(cnt).getEmployee().getName());
		assertThat(actuals).extracting(a->a.getAbsenceReason().getName()).contains(expecteds.get(cnt).getAbsenceReason().getName());
		}
	}

	@Test
	void test_selectByApplicationId() {
		AbsenceApplication expected=AbsenceApplicationTestDataGenerator.getAnyEmployeeAbsenceApplication(EMPLOYEE.hoge, LocalDate.of(2025, 4, 1),new AbsenceReason(1,"理由1"), "詳細1");
		AbsenceApplication actual=mapper.selectByApplicationId(1);
		assertThat(actual.getEmployee().getName()).isEqualTo(expected.getEmployee().getName());
		assertThat(actual.getShiftSchedule().getDate()).isEqualTo(expected.getShiftSchedule().getDate());
		assertThat(actual.getAbsenceReason().getName()).isEqualTo(expected.getAbsenceReason().getName());
		assertThat(actual.getDetail()).isEqualTo(expected.getDetail());
	}
	
	@Test
	void test_selectAllReasons() {
		List<AbsenceReason> expecteds=AbsenceReasonTestDataGenerator.getAllReasons();
		List<AbsenceReason> actuals=mapper.selectAllReasons();
		assertThat(actuals.size()).isEqualTo(expecteds.size());
		assertThat(actuals).containsExactlyInAnyOrderElementsOf(actuals);
	}
	
	@Test
	void test_insert() {
		AbsenceApplication expected=AbsenceApplicationTestDataGenerator.createAbsenceApplication(5, new AbsenceReason(1,"理由1"), "詳細1");
		mapper.insert(expected);
		String query="SELECT\n"
				+ "		  shift_id\n"
				+ "		  ,ar.name\n"
				+ "		  ,aa.detail\n"
				+ "		FROM\n"
				+ "		  absence_applications AS aa\n"
				+ "		  INNER JOIN shift_schedules AS s\n"
				+ "		  ON aa.shift_id=s.id\n"
				+ "		  INNER JOIN employees AS e\n"
				+ "		  ON s.employee_id=e.id\n"
				+ "		  INNER JOIN absence_reasons AS ar\n"
				+ "		  ON aa.reason_id=ar.id\n"
				+ "		WHERE\n"
				+ "		  aa.id= ?\n"
				+ "		;";
		Map<String,Object> actual=jdbcTemplate.queryForMap(query,5);
		assertThat(actual.get("shift_id")).isEqualTo(expected.getShiftSchedule().getShiftId());
		assertThat(actual.get("name")).isEqualTo(expected.getAbsenceReason().getName());
		assertThat(actual.get("detail")).isEqualTo(expected.getDetail());
	}

	@Test
	void test_insert_invalid_because_shift_id_is_duplicated() {
		AbsenceApplication expected=AbsenceApplicationTestDataGenerator.createAbsenceApplication(1, new AbsenceReason(1,"理由1"), "詳細1");
		assertThrows(DataIntegrityViolationException.class,()->mapper.insert(expected));
	}
//	JdbcTemplateを用い、承認状況がnullの欠勤申請IDを抽出し、updateを実行。
//	その後、そのIDを持つ欠勤申請の承認状況のみを抽出し、trueになっているか確認
	@Test
	void test_update() {
		String isApproveIsNull="SELECT id FROM absence_applications WHERE is_approve is null;";
		Integer target=jdbcTemplate.queryForObject(isApproveIsNull, Integer.class);
		mapper.update(target, true);
		String idEqualsTarget="SELECT is_approve FROM absence_applications WHERE id=?;";
		Boolean actual=jdbcTemplate.queryForObject(idEqualsTarget, Boolean.class,target);
		assertThat(actual).isEqualTo(true);
	}
	
	@Test
	void test_delete() {
		String confirmNotNull="SELECT 1 FROM absence_applications WHERE id=?;";
		Integer before=jdbcTemplate.queryForObject(confirmNotNull, Integer.class,3);
		assertThat(before).isNotNull();
		mapper.delete(3);
		assertThrows(EmptyResultDataAccessException.class, ()->jdbcTemplate.queryForObject(confirmNotNull, Integer.class,3));
	}
}
