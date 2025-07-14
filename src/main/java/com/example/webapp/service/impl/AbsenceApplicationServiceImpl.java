package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.exception.DuplicateApplicationException;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.AbsenceApplicationForm;
import com.example.webapp.helper.AbsenceApplicationHelper;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.service.AbsenceApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenceApplicationServiceImpl implements AbsenceApplicationService {

	private final AbsenceApplicationMapper absenceApplicationMapper;

	@Override
	public List<AbsenceApplication> get(String fromPage, Integer employeeId) throws InvalidAccessException {
		if (fromPage.equals("admin")) {
			return absenceApplicationMapper.selectAll();
		}
		if (fromPage.equals("user")) {
			return absenceApplicationMapper.selectAllByEmployeeId(employeeId);
		}
		throw new InvalidAccessException("遷移元ページが不明です");
	}

	@Override
	public List<AbsenceApplication> getTodayApplications() {
		return absenceApplicationMapper.selectTodayAndIsApproveEqualsNull();
	}

	@Override
	public List<AbsenceReason> getAllReasons() {
		return absenceApplicationMapper.selectAllReasons();
	}

	@Override
	public void submitApplication(AbsenceApplicationForm form) throws DuplicateApplicationException {
		AbsenceApplication application = AbsenceApplicationHelper.convertAbsenceApplication(form);
		try {
			absenceApplicationMapper.insert(application);
		} catch (DataIntegrityViolationException e) {
			throw new DuplicateApplicationException("すでに申請済みです");
		}
	}

	@Override
	public void updateApprove(Integer shiftId, Boolean decision) {
		absenceApplicationMapper.update(shiftId, decision);
	}

	@Override
	public void deleteApplication(Integer applicationId) throws InvalidEditException {
		AbsenceApplication application = absenceApplicationMapper.selectByApplicationId(applicationId);
		Boolean isPast = application.getShiftSchedule().getDate().isBefore(LocalDate.now());
		if (isPast) {
			throw new InvalidEditException("過去の申請は削除できません");
		}
		absenceApplicationMapper.delete(applicationId);
	}
}
