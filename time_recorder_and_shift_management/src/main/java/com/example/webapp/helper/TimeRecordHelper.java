package com.example.webapp.helper;

import com.example.webapp.entity.TimeRecord;
import com.example.webapp.form.TimeRecordForm;

public class TimeRecordHelper {
	
	public static TimeRecordForm convertTimeRecordForm(TimeRecord entity) {
		TimeRecordForm form=new TimeRecordForm();
		form.setDate(entity.getDate());
		form.setEmployeeId(entity.getEmployeeId());
		form.setEmployeeName(entity.getEmployeeName());
		form.setClockIn(entity.getClockIn());
		form.setClockOut(entity.getClockOut());
		return form;
	}
	
	public static TimeRecord convertTimeRecord(TimeRecordForm form) {
		TimeRecord entity=new TimeRecord();
		entity.setDate(form.getDate());
		entity.setEmployeeId(form.getEmployeeId());
		entity.setEmployeeName(form.getEmployeeName());
		entity.setClockIn(form.getClockIn());
		entity.setClockOut(form.getClockOut());
		return entity;
	}
}
