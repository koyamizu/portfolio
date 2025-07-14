package com.example.webapp.test_data;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.test_data.absence_applicaiton.FugaAbsenceApplication;
import com.example.webapp.test_data.absence_applicaiton.HogeAbsenceApplication;
import com.example.webapp.test_data.employee.EMPLOYEE;

public class AbsenceApplicationTestDataGenerator {
	
	public static AbsenceApplication getAnyEmployeeAbsenceApplication(EMPLOYEE name,LocalDate date,AbsenceReason reason,String detail) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeAbsenceApplication(date, reason, detail);
		case EMPLOYEE.fuga:
			return new FugaAbsenceApplication(date, reason, detail);
		default:
			break;
		}
		return null;
	}
	
	public static AbsenceApplication createAbsenceApplication(Integer shiftId,AbsenceReason reason,String detail) {
		var aa=new AbsenceApplication();
		var s=new ShiftSchedule();
		s.setShiftId(shiftId);
		aa.setShiftSchedule(s);
		aa.setAbsenceReason(reason);
		aa.setDetail(detail);
		return aa;
	}
	
	public static List<AbsenceApplication> getAllApplication(){
		return List.of(
				new HogeAbsenceApplication(LocalDate.of(2025,4,1), new AbsenceReason(1,"理由1"), "詳細1",true)
				,new HogeAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細2",true)
				,new FugaAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細3",null)
				,new HogeAbsenceApplication(LocalDate.now().plusDays(1), new AbsenceReason(3,"理由3"), "詳細4",false)
				);
	}
	
	public static List<AbsenceApplication> getAllHogeApplication(){
		return List.of(
				new HogeAbsenceApplication(LocalDate.of(2025,4,1), new AbsenceReason(1,"理由1"), "詳細1",true)
				,new HogeAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細2",true)
				,new HogeAbsenceApplication(LocalDate.now().plusDays(1), new AbsenceReason(3,"理由3"), "詳細4",false)
				);
	}
	
	public static List<AbsenceApplication> getTodayApplication(){
		return List.of(
				new FugaAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細3",null)
				);
	}
}
