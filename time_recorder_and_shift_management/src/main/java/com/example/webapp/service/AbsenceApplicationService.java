package com.example.webapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Absence;
import com.example.webapp.entity.AbsenceApplication;

@Service
public interface AbsenceApplicationService {

	List<AbsenceApplication> selectAllApplicationsAfterToday();
	
	List<AbsenceApplication> selectPersonalApplicationsAfterToday(Integer employeeId);
	
	List<Absence> selectAllReasons();
	
	void insertApplication(AbsenceApplication application);
	
	void updateApprove(Integer shiftId,Boolean decision);
	
	void deleteApplication(Integer applicationId);
}
