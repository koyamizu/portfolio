package com.example.webapp.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
		

		
		
		ShiftSchedule shift_No1_today=new ShiftSchedule(1,LocalDate.now(),LocalTime.of(6,0,0),LocalTime.of(9,0,0),null,null,null);
		ShiftSchedule shift_No2_today=new ShiftSchedule(1,LocalDate.now(),LocalTime.of(6,0,0),LocalTime.of(9,0,0),null,null,null);
	}
	
	public AbsenceApplication getKoga2025_06_02ApplicationData() {
		Employee hoge=new Employee();
		hoge.setEmployeeId(1001);
		hoge.setName("hoge");
		ShiftSchedule s=new ShiftSchedule();
		s.setShiftId(1);
		s.setDate(LocalDate.of(2025, 6, 2));
		AbsenceReason a=new AbsenceReason();
		a.setName("理由1");
		AbsenceApplication application=new AbsenceApplication(
				1,s,hoge,a,"詳細1",null,LocalDateTime.of(2025,1,1,0,0,0),LocalDateTime.of(2025,12,31,23,59,59)
				);
		application.setDetail("詳細1");
		application.setIsApprove(true);
		application.setCreatedAt(LocalDateTime.of(2025,1,1,0,0,0));
		application.setUpdatedAt(LocalDateTime.of(2025,12,31,23,59,59));
		return application;
	}
	
//	SELECT
//	  aa.id
//	  ,aa.shift_id
//	  ,s.date
//	  ,e.name AS employee_name
//	  ,aa.created_at
//	  ,aa.updated_at
//	  ,aa.is_approve
//	  ,ar.name AS reason_name
//	  ,aa.detail
//	FROM
//	  absence_applications AS aa
//	  INNER JOIN shift_schedules AS s
//	  ON aa.shift_id=s.id
//	  INNER JOIN employees AS e
//	  ON s.employee_id=e.id
//	  INNER JOIN absence_reasons AS ar
//	  ON aa.reason_id=ar.id
//	ORDER BY aa.id ASC
//	;
	public List<AbsenceApplication> getAbsenceApplicationData(){
		Employee hoge=new Employee();
		hoge.setEmployeeId(1001);
		hoge.setName("hoge");
		AbsenceReason a=new AbsenceReason();
		a.setName("理由1");
		List<AbsenceApplication> apps=new ArrayList<AbsenceApplication>();
		apps.add(0, new AbsenceApplication(1,new ShiftSchedule(1, LocalDate.of(2025, 6, 2), null, null, null, null, null)
				,hoge,a,"詳細1",null,null,null));
		apps.add(1, new AbsenceApplication(2,null,null,null,"詳細2",null,null,null));
		apps.add(2, new AbsenceApplication(3,null,null,null,"詳細3",null,null,null));
		return apps;
	}
}
