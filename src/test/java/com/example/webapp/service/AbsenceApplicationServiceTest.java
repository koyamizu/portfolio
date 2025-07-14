package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.configuration.AbsenceApplicationTestData;
import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.service.impl.AbsenceApplicationServiceImpl;

@ExtendWith(MockitoExtension.class)
public class AbsenceApplicationServiceTest {

	@InjectMocks
	AbsenceApplicationServiceImpl service;
	@Mock
	AbsenceApplicationMapper mapper;
	private AbsenceApplicationTestData data;
	
	@BeforeEach
	void set() {
		data=new AbsenceApplicationTestData();
	}
	@Test
	void test_get_fromPage_is_admin() throws InvalidAccessException {
		List<AbsenceApplication> expects=data.getAbsenceApplicationData();
		doReturn(expects).when(mapper).selectAll();
		List<AbsenceApplication> actuals=service.get("admin",1001);
		assertThat(actuals).containsAll(expects);
	}
	@Test
	void test_get_fromPage_is_user() throws InvalidAccessException {
		List<AbsenceApplication> expects=data.getAbsenceApplicationData();
		doReturn(expects).when(mapper).selectAllByEmployeeId(1002);
		List<AbsenceApplication> actuals=service.get("admin",1002);
		assertThat(actuals).containsAll(expects);
	}
	
//	List<AbsenceApplication> get(String fromPage, Integer employeeId) throws InvalidAccessException;
//	
//	List<AbsenceApplication> getTodayApplications();
//	
//	List<AbsenceReason> getAllReasons();
//	
//	void submitApplication(AbsenceApplicationForm form) throws DuplicateApplicationException;
//	
//	void updateApprove(Integer shiftId,Boolean decision);
//	
//	void deleteApplication(Integer applicationId) throws InvalidEditException;
}
