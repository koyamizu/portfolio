package com.example.webapp.common;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;

public class AbsenceApplicationTestData {
	
	public AbsenceApplication getKoga2025_06_02ApplicationData() {
		ShiftSchedule s=new ShiftSchedule();
		AbsenceReason a=new AbsenceReason();
		AbsenceApplication application=new AbsenceApplication();
		s.setShiftId(27);
		a.setReasonId(1);
		application.setDetail("就職面接のため");
		application.setIsApprove(true);
//		application.setCreatedAt(LocalDateTime.parse("2025-06-02 14:25:43", ""));
		return application;
	}
}
