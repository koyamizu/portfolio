package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;

@MybatisTest
@Sql("AbsenceApplicationMapperTest.sql")
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AbsenceApplicationMapperTest {
	
	@Autowired
	private AbsenceApplicationMapper mapper;
	
	@Test
	void test_selectAll() {
		 List<AbsenceApplication> actuals=mapper.selectAll();
		 assertThat(actuals.size()).isEqualTo(3);
	}
	
	@Test
	void test_selectAllByEmployeeId() {
		List<AbsenceApplication> actuals=mapper.selectAllByEmployeeId(1001);
		assertThat(actuals.size()).isEqualTo(2);
		AbsenceApplication confirm
		 = actuals.stream().filter(a->a.getShiftSchedule().getShiftId().equals(1)).findFirst().orElse(null);
		assertThat(confirm.getApplicationId()).isEqualTo(1);
		assertThat(confirm.getShiftSchedule().getDate()).isEqualTo(LocalDate.now());
		assertThat(confirm.getEmployee().getName()).isEqualTo("hogehoge");
		assertThat(confirm.getAbsenceReason().getName()).isEqualTo("理由1");
		assertThat(confirm.getDetail()).isEqualTo("詳細1");
		assertThat(confirm.getIsApprove()).isEqualTo(null);
	}
	
	@Test
	void test_selectToday() {
		List<AbsenceApplication> actuals=mapper.selectTodayAndIsApproveEqualsNull();
		AbsenceApplication actual=actuals.get(0);
		assertThat(actual.getApplicationId()).isEqualTo(1);
		assertThat(actual.getEmployee().getName()).isEqualTo("hogehoge");
		assertThat(actual.getAbsenceReason().getName()).isEqualTo("理由1");
	}

	@Test
	void test_selectByApplicationId() {
		AbsenceApplication actual=mapper.selectByApplicationId(1);
		assertThat(actual.getEmployee().getName()).isEqualTo("hogehoge");
		assertThat(actual.getShiftSchedule().getDate()).isEqualTo(LocalDate.now());
		assertThat(actual.getAbsenceReason().getName()).isEqualTo("理由1");
		assertThat(actual.getDetail()).isEqualTo("詳細1");
	}
	
	@Test
	void test_selectAllReasons() {
		List<AbsenceReason> actuals=mapper.selectAllReasons();
		assertThat(actuals.size()).isEqualTo(3);
		AbsenceReason confirm=new AbsenceReason(1,"理由1");
		assertThat(actuals).contains(confirm);
	}
	
	@Test
	void test_insert() {
		var data=new AbsenceApplication();
		var s=new ShiftSchedule();
		var a=new AbsenceReason();
		s.setShiftId(4);
		a.setReasonId(1);
		data.setShiftSchedule(s);
		data.setAbsenceReason(a);
		data.setDetail("詳細4");
		mapper.insert(data);
		AbsenceApplication actual=mapper.selectByApplicationId(4);
		assertThat(actual).isNotNull();
	}
	
	@Test
	void test_update() {
		mapper.update(1, true);
//		selectByApplicationId()の戻り値はisApproveを含まない
		//jdbcTemplateに書き換える
		List<AbsenceApplication> actuals=mapper.selectAll();
		AbsenceApplication actual=actuals.stream().filter(a->a.getShiftSchedule().getShiftId().equals(1)).findFirst().orElse(null);
		assertThat(actual.getIsApprove()).isEqualTo(true);
		mapper.update(1, false);
		actuals=mapper.selectAll();
		actual=actuals.stream().filter(a->a.getShiftSchedule().getShiftId().equals(1)).findFirst().orElse(null);
		assertThat(actual.getIsApprove()).isEqualTo(false);
	}
	
	@Test
	void test_delete() {
		AbsenceApplication target=mapper.selectByApplicationId(3);
		assertThat(target).isNotNull();
		mapper.delete(3);
		AbsenceApplication actual=mapper.selectByApplicationId(3);
		assertThat(actual).isNull();
	}
}
