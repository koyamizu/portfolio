package com.example.webapp.helper;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.form.AbsenceApplicationForm;

public class AbsenceApplicationHelper {

	public static AbsenceApplication convertAbsenceApplication(AbsenceApplicationForm form) {
		AbsenceApplication entity=new AbsenceApplication();
		entity.setApplicationId(form.getApplicationId());
		entity.setShiftSchedule(form.getShiftSchedule());
		entity.setEmployee(form.getEmployee());
		entity.setAbsenceReason(form.getAbsenceReason());
		entity.setDetail(form.getDetail());
		entity.setIsApprove(form.getIsApprove());
		return entity;
	}
	
	public static AbsenceApplicationForm convertAbsenceApplicationForm(AbsenceApplication entity) {
		AbsenceApplicationForm form=new AbsenceApplicationForm();
		form.setApplicationId(entity.getApplicationId());
		form.setShiftSchedule(entity.getShiftSchedule());
		form.setEmployee(entity.getEmployee());
		form.setAbsenceReason(entity.getAbsenceReason());
		form.setDetail(entity.getDetail());
		form.setIsApprove(entity.getIsApprove());
		return form;
	}
}
