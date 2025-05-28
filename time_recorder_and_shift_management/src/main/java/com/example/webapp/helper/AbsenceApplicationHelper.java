package com.example.webapp.helper;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.form.AbsenceApplicationForm;

public class AbsenceApplicationHelper {

	public static AbsenceApplication convertAbsenceApplication(AbsenceApplicationForm form) {
		AbsenceApplication entity=new AbsenceApplication();
		entity.setId(form.getId());
		entity.setShiftSchedule(form.getShiftSchedule());
		entity.setEmployee(form.getEmployee());
		entity.setAbsence(form.getAbsence());
		entity.setDetail(form.getDetail());
		entity.setIsApprove(form.getIsApprove());
		return entity;
	}
	
	public static AbsenceApplicationForm convertAbsenceApplicationForm(AbsenceApplication entity) {
		AbsenceApplicationForm form=new AbsenceApplicationForm();
		form.setId(entity.getId());
		form.setShiftSchedule(entity.getShiftSchedule());
		form.setEmployee(entity.getEmployee());
		form.setAbsence(entity.getAbsence());
		form.setDetail(entity.getDetail());
		form.setIsApprove(entity.getIsApprove());
		return form;
	}
}
