package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateApplicationException;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.AbsenceApplicationForm;
import com.example.webapp.helper.AbsenceApplicationHelper;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.service.impl.AbsenceApplicationServiceImpl;
import com.example.webapp.test_data.AbsenceApplicationTestDataGenerator;
import com.example.webapp.test_data.AbsenceReasonTestDataGenerator;
import com.example.webapp.test_data.TimeRecorderTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;

@ExtendWith(MockitoExtension.class)
public class AbsenceApplicationServiceTest {

	@InjectMocks
	AbsenceApplicationServiceImpl service;
	@Mock
	AbsenceApplicationMapper mapper;
	
//	get()は「/absence-application」にアクセスした時に実行されるメソッド。
//	URLパスとして従業員IDが渡されるわけではなく、認証情報（org.springframework.security.core.Authentication）から従業員IDを取得している
//	ので、存在しない従業員IDが渡されるパターンを検証する必要はない。（存在しないアカウントにはそもそもログインできない）
	@Test
	void test_get_fromPage_is_admin() throws InvalidAccessException {
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getAllApplication();
		doReturn(expecteds).when(mapper).selectAll();
		List<AbsenceApplication> actuals=service.get("admin",1001);
		assertThat(actuals).containsAll(expecteds);
	}
	@Test
	void test_get_fromPage_is_user() throws InvalidAccessException {
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getAllHogeApplication();
		doReturn(expecteds).when(mapper).selectAllByEmployeeId(1001);
		List<AbsenceApplication> actuals=service.get("user",1001);
		assertThat(actuals).containsAll(expecteds);
	}
	
//	異常系。fromPageがadminやuser以外。通常はあり得ないが、直URLで推移しようとしたら起こる。
	@Test
	void test_get_invalid_because_fromPage_is_other(){
		assertThrows(InvalidAccessException.class,()->service.get("hoge",1001));
	}
	
	@Test
	void test_getTodayApplications() {
		List<AbsenceApplication> expecteds=AbsenceApplicationTestDataGenerator.getTodayApplication();
		doReturn(expecteds).when(mapper).selectTodayAndIsApproveEqualsNull();
		List<AbsenceApplication> actuals=service.getTodayApplications();
		assertThat(actuals).containsAll(expecteds);
	}

	@Test
	void test_getAllReasons() {
		List<AbsenceReason> expecteds=AbsenceReasonTestDataGenerator.getAllReasons();
		doReturn(expecteds).when(mapper).selectAllReasons();
		List<AbsenceReason> actuals=service.getAllReasons();
		assertThat(actuals).containsAll(expecteds);
	}
	
	@Test
	void test_submitApplication() throws DuplicateApplicationException {
		AbsenceApplication expected=AbsenceApplicationTestDataGenerator.createAbsenceApplication(5, new AbsenceReason(1,"理由1"), "詳細1");
		doNothing().when(mapper).insert(expected);
		AbsenceApplicationForm expectedForm=AbsenceApplicationHelper.convertAbsenceApplicationForm(expected);
		service.submitApplication(expectedForm);
		verify(mapper).insert(expected);
	}
	
//	異常系。同じシフトに対して重複して欠勤申請を提出しようとした
	@Test
	void test_submitApplication_throws_DuplicateApplicationException(){
		AbsenceApplication expected=AbsenceApplicationTestDataGenerator.createAbsenceApplication(1, new AbsenceReason(1,"理由1"), "詳細1");
		doThrow(DataIntegrityViolationException.class).when(mapper).insert(expected);
		AbsenceApplicationForm expectedForm=AbsenceApplicationHelper.convertAbsenceApplicationForm(expected);
		assertThrows(DuplicateApplicationException.class,()->service.submitApplication(expectedForm));
	}
	
	@Test
	void test_updateApprove() {
		doNothing().when(mapper).update(3, true);
		service.updateApprove(3, true);
		verify(mapper).update(any(),any());
	}
	
	@Test
	void test_deleteApplication() throws InvalidEditException {
		AbsenceApplication dummy=AbsenceApplicationTestDataGenerator.createAbsenceApplication(1, new AbsenceReason(1,"理由1"), "詳細1");
//		本当はShiftManagementTestDataGeneratorからデータを生成したかったが、戻り値の型がFullCalendarEntityだったのでTimeRecorderTestDataGeneratorから生成した。
//		TimeRecorderという名前は直感に反するので、そこの整合性を取りたい。
		ShiftSchedule hogeTodayShift=TimeRecorderTestDataGenerator.getAnyEmployeeTimeRecorderTable(EMPLOYEE.hoge);
		dummy.setShiftSchedule(hogeTodayShift);
		doReturn(dummy).when(mapper).selectByApplicationId(1);
		doNothing().when(mapper).delete(1);
		service.deleteApplication(1);
		verify(mapper).delete(1);
	}
	
//	異常系。今日より前の申請を削除しようとした。
	@Test
	void test_deleteApplication_throws_InvalidEditException() throws InvalidEditException{
		AbsenceApplication dummy=AbsenceApplicationTestDataGenerator.createAbsenceApplication(1, new AbsenceReason(1,"理由1"), "詳細1");
//		本当はShiftManagementTestDataGeneratorからデータを生成したかったが、戻り値の型がFullCalendarEntityだったのでTimeRecorderTestDataGeneratorから生成した。
//		TimeRecorderという名前は直感に反するので、そこの整合性を取りたい。
		ShiftSchedule hogePastShift=TimeRecorderTestDataGenerator.getAnyEmployeeTimeRecorderTable(EMPLOYEE.hoge);
		hogePastShift.setDate(LocalDate.now().minusDays(1));
		dummy.setShiftSchedule(hogePastShift);
		doReturn(dummy).when(mapper).selectByApplicationId(1);
		assertThrows(InvalidEditException.class,()->service.deleteApplication(1));
	}
}
