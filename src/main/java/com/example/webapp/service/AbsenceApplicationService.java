package com.example.webapp.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;

@Service
public interface AbsenceApplicationService {

//	List<AbsenceApplication> selectAllApplicationsAfterToday();
//	
//	List<AbsenceApplication> selectPersonalApplicationsAfterToday(Integer employeeId);
	
	List<AbsenceApplication> get(Authentication auth);
	
	List<AbsenceApplication> getTodayApplications();
	
	List<AbsenceApplication> getApplicationDetail();
	
	List<AbsenceReason> selectAllReasons();
	
	void insertApplication(AbsenceApplication application);
	
	void updateApprove(Integer shiftId,Boolean decision);
	
	void deleteApplication(Integer applicationId);
}
