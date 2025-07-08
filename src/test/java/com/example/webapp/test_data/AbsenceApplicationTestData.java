package com.example.webapp.test_data;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.test_data.absence_applicaiton.FugaAbsenceApplication;
import com.example.webapp.test_data.absence_applicaiton.HogeAbsenceApplication;
import com.example.webapp.test_data.employee.EMPLOYEE;

public class AbsenceApplicationTestData {
	
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
	public static List<AbsenceApplication> getAllApplication(){
		return List.of(
				new HogeAbsenceApplication(LocalDate.now(), new AbsenceReason(1,"理由1"), "詳細1")
				,new FugaAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細2",true)
				,new HogeAbsenceApplication(LocalDate.now().plusDays(1), new AbsenceReason(2,"理由3"), "詳細3",false)
				);
	}
	
	public static List<AbsenceApplication> getAllHogeApplication(){
		return List.of(
				new HogeAbsenceApplication(LocalDate.now(), new AbsenceReason(1,"理由1"), "詳細1")
				,new HogeAbsenceApplication(LocalDate.now().plusDays(1), new AbsenceReason(2,"理由3"), "詳細3",false)
				);
	}
	
	public static List<AbsenceApplication> getTodayApplication(){
		return List.of(
				new HogeAbsenceApplication(LocalDate.now(), new AbsenceReason(1,"理由1"), "詳細1")
				,new FugaAbsenceApplication(LocalDate.now(), new AbsenceReason(2,"理由2"), "詳細2",true)
				);
	}
}
