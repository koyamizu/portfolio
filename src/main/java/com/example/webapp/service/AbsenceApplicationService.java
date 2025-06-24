package com.example.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.exception.DuplicateApplicationException;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.AbsenceApplicationForm;

@Service
public interface AbsenceApplicationService {

//	List<AbsenceApplication> selectAllApplicationsAfterToday();
//	
//	List<AbsenceApplication> selectPersonalApplicationsAfterToday(Integer employeeId);
	
	List<AbsenceApplication> get(String fromPage, Integer employeeId) throws InvalidAccessException;
	
	List<AbsenceApplication> getTodayApplications();
	
//	List<AbsenceApplication> getApplicationDetail();
	
	List<AbsenceReason> selectAllReasons();
	
	void submitApplication(AbsenceApplicationForm form) throws DuplicateApplicationException;
	
	void updateApprove(Integer shiftId,Boolean decision);
	
	void deleteApplication(Integer applicationId) throws InvalidEditException;
}
