package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;

import com.example.webapp.entity.AbsenceApplication;

@MybatisTest
@Sql("AbsenceApplicationMapperTest.sql")
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
public class AbsenceApplicationMapperTest {
	
	@Autowired
	private AbsenceApplicationMapper mapper;
	
	@Test
	void test_selectAll() {
		 List<AbsenceApplication> actuals=mapper.selectAll();
		 assertThat(actuals.size()).isEqualTo(4);
	}
	
	@Test
	void test_selectAllByEmployeeId() {
//				new AbsenceApplication(
//				  27, 1, "就職面接のため", true, "2025-06-02 14:25:43", "2025-06-02 14:25:43"
//				);
		
		List<AbsenceApplication> actuals=mapper.selectAllByEmployeeId(1002);
		assertThat(actuals.size()).isEqualTo(2);
		AbsenceApplication confirm
		 = actuals.stream().filter(a->a.getShiftSchedule().getShiftId().equals(27)).findFirst().orElse(null);
		assertThat(confirm.getShiftSchedule().getShiftId()).isEqualTo(27);
		assertThat(confirm.getAbsenceReason().getReasonId()).isEqualTo(1);
		assertThat(confirm.getDetail()).isEqualTo("就職面接のため");
		assertThat(confirm.getIsApprove()).isEqualTo(true);
		assertThat(confirm.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss"))).isEqualTo("2025-06-02 14:25:43");
		assertThat(confirm.getUpdatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss"))).isEqualTo("2025-06-02 14:25:43");
	}
}
