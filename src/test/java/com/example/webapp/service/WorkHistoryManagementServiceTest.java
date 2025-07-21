package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.form.TimeRecordForm;
import com.example.webapp.helper.TimeRecordHelper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.impl.WorkHistoryManagementServiceImpl;
import com.example.webapp.test_data.WorkHistoryManagementTestDataGenerator;

@ExtendWith(MockitoExtension.class)
public class WorkHistoryManagementServiceTest {

	@InjectMocks
	WorkHistoryManagementServiceImpl service;
	@Mock
	WorkHistoryManagementMapper mapper;
	
	@Test
	void test_getAllWorkHistoriesToDate() {
		List<TimeRecord> expecteds=WorkHistoryManagementTestDataGenerator.getAllWorkHistory();
		Integer month=expecteds.get(0).getDate().getMonthValue();
		doReturn(expecteds).when(mapper).selectAllByMonth(month);
		List<TimeRecord> actuals=service.getAllWorkHistoriesToDate(month);
		assertThat(actuals).isEqualTo(expecteds);
	}

	@Test
	void test_getPersonalWorkHistoriesToDateByEmployeeIdAndMonth() {
		List<TimeRecord> expecteds=WorkHistoryManagementTestDataGenerator.getFugaWorkHistoryOfMay();
		Integer employeeId=expecteds.get(0).getEmployeeId();
		Integer month=expecteds.get(0).getDate().getMonthValue();
		doReturn(expecteds).when(mapper).selectByEmployeeIdAndMonth(employeeId, month);
		List<TimeRecord> actuals=service.getPersonalWorkHistoriesToDateByEmployeeIdAndMonth(employeeId, month);
		assertThat(actuals).isEqualTo(expecteds);
	}

	@Test
	void test_getWorkedEmployeesByMonth() {
		List<Employee> expecteds=WorkHistoryManagementTestDataGenerator.getEmployeeIdAndNameWhoWorkedInMay();
		doReturn(expecteds).when(mapper).selectEmployeeByMonth(5);
		List<Employee> actuals=service.getWorkedEmployeesByMonth(5);
		assertThat(actuals).isEqualTo(expecteds);		
	}
	
	@Test
	void test_getWorkHistoryDetailByEmployeeIdAndDate() throws NoDataFoundException {
		TimeRecord expected=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 11);
		Integer employeeId=expected.getEmployeeId();
		LocalDate date=expected.getDate();
		doReturn(expected).when(mapper).selectByEmployeeIdAndDate(employeeId,date);
		TimeRecordForm actual=service.getWorkHistoryDetailByEmployeeIdAndDate(employeeId, date);
		assertThat(actual).isEqualTo(TimeRecordHelper.convertTimeRecordForm(expected));
	}
	
//	異常系。勤怠履歴が見つからなかった。
	@Test
	void test_getWorkHistoryDetailByEmployeeIdAndDate_throws_NoDataException (){
		TimeRecord expected=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 11);
		Integer employeeId=expected.getEmployeeId();
		LocalDate date=expected.getDate();
		doReturn(null).when(mapper).selectByEmployeeIdAndDate(employeeId,date);
		assertThrows(NoDataFoundException.class,()->service.getWorkHistoryDetailByEmployeeIdAndDate(employeeId, date));
	}

	@Test
	void test_updateWorkHistory() throws InvalidEditException {
		TimeRecord updatedHistory=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 1);
		updatedHistory.setClockIn(LocalTime.of(5,50,01));
		updatedHistory.setClockOut(LocalTime.of(9,10,01));
		doNothing().when(mapper).update(updatedHistory);
		service.updateWorkHistory(TimeRecordHelper.convertTimeRecordForm(updatedHistory));
		verify(mapper).update(updatedHistory);
	}
	
	@Test
	void test_updateWorkHistory_throws_InvalidEditException (){
		TimeRecord updatedHistory=WorkHistoryManagementTestDataGenerator.getFugaWorkHistory(5, 1);
		updatedHistory.setClockOut(LocalTime.of(5,50,01));
		updatedHistory.setClockIn(LocalTime.of(9,10,01));
		assertThrows(InvalidEditException.class,()->service.updateWorkHistory(TimeRecordHelper.convertTimeRecordForm(updatedHistory)));
	}
}
