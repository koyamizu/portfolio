package com.example.webapp.common;

import java.util.ArrayList;
import java.util.List;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;

public class AbsenceApplicationTestData {
	
	private AbsenceApplication employee_1001_hogehoge_today_reason_1;
	private AbsenceApplication employee_1002_fugafuga_today_reason_1;
	private AbsenceApplication employee_1001_hogehoge_tomorrow_reason_2;
	
	public AbsenceApplicationTestData() {
		Employee emp_1001=new Employee();
		Employee emp_1002=new Employee();
		emp_1001.setEmployeeId(1001);
		emp_1001.setName("hogehoge");
		emp_1002.setEmployeeId(1002);
		emp_1002.setName("fugafuga");
		ShiftSchedule shift_1_today=new ShiftSchedule;
	}
	
	public AbsenceApplication getKoga2025_06_02ApplicationData() {
		ShiftSchedule s=new ShiftSchedule();
		AbsenceReason a=new AbsenceReason();
		AbsenceApplication application=new AbsenceApplication();
		s.setShiftId(1);
		a.setReasonId(1);
		application.setDetail("詳細1");
		application.setIsApprove(true);
//		application.setCreatedAt(LocalDateTime.parse("2025-06-02 14:25:43", ""));
		return application;
	}
	
	public List<AbsenceApplication> getAbsenceApplicationData(){
		List<AbsenceApplication> apps=new ArrayList<AbsenceApplication>();
		apps.add(0, new AbsenceApplication(1,new ShiftSchedule(1, null, null, null, null, null, null),emp_1001,null,"詳細1",null,null,null));
		apps.add(1, new AbsenceApplication(2,null,null,null,"詳細2",null,null,null));
		apps.add(2, new AbsenceApplication(3,null,null,null,"詳細3",null,null,null));
		return apps;
	}
}
