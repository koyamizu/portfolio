package com.example.webapp.service.impl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.Role;
import com.example.webapp.form.AbsenceApplicationForm;
import com.example.webapp.helper.AbsenceApplicationHelper;
import com.example.webapp.repository.AbsenceApplicationMapper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.AbsenceApplicationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AbsenceApplicationServiceImpl implements AbsenceApplicationService {

	private final AbsenceApplicationMapper absenceApplicationMapper;
	private final EmployeesManagementMapper employeeManagementMapper;

//	@Override
//	public List<AbsenceApplication> selectAllApplicationsAfterToday() {
//		return absenceApplicationMapper.selectAll();
//	}
//
//	@Override
//	public List<AbsenceApplication> selectPersonalApplicationsAfterToday(Integer employeeId) {
//		if (employeeId.equals(null)) {
//			throw new RuntimeException("認証情報がありません");
//		}
//		List<Integer> employeeIds = employeeManagementMapper.selectAllIdAndName().stream().map(e -> e.getEmployeeId())
//				.toList();
//		if (!employeeIds.contains(employeeId)) {
//			throw new RuntimeException("そのIDを持つ従業員は存在しません");
//		}
//		return absenceApplicationMapper.selectAllByEmployeeId(employeeId);
//	}
//	
	@Override
	public List<AbsenceApplication> getTodayApplications(){
		return absenceApplicationMapper.selectToday();
	}
	
	@Override
	public List<AbsenceApplication> getApplicationDetail(){
		return 	absenceApplicationMapper.selectByApplicationId();
	}

	@Override
	public List<AbsenceReason> getAllReasons() {
		return absenceApplicationMapper.selectAllReasons();
	}

	@Override
	public void submitApplication(AbsenceApplicationForm applicationForm) {
		AbsenceApplication application = AbsenceApplicationHelper.convertAbsenceApplication(applicationForm);
		absenceApplicationMapper.insert(application);
	}

	@Override
	public void updateApprove(Integer shiftId, Boolean decision) {
			absenceApplicationMapper.update(shiftId, decision);
	}

	@Override
	public void deleteApplication(Integer applicationId) {
		absenceApplicationMapper.delete(applicationId);
	}

	@Override
	public List<AbsenceApplication> get(Authentication auth) {
		Boolean isAdmin=auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if (isAdmin) {
			return absenceApplicationMapper.selectAll();
		} else {
			Integer employeeId = Integer.parseInt(auth.getName());
			return absenceApplicationMapper.selectAllByEmployeeId(employeeId);
		}
	}
}
