package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.service.AbsenceApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenceApplicationServiceImpl implements AbsenceApplicationService {

	private final AbsenceApplicationMapper mapper;
	@Override
	public List<AbsenceApplication> selectAllApplicationsAfterToday() {
		return mapper.selectAll();
	}

	@Override
	public List<AbsenceApplication> selectPersonalApplicationsAfterToday(Integer employeeId) {
		return mapper.selectAllByEmployeeId(employeeId);
	}

	@Override
	public List<AbsenceReason> selectAllReasons() {
		return mapper.selectAllReasons();
	}

	@Override
	public void insertApplication(AbsenceApplication application) {
		mapper.insert(application);
	}

	@Override
	public void updateApprove(Integer shiftId, Boolean decision) {
		mapper.update(shiftId, decision);
	}

	@Override
	public void deleteApplication(Integer applicationId) {
		mapper.delete(applicationId);
	}
}
