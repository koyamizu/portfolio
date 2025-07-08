package com.example.webapp.test_data;

import java.time.LocalDate;
import java.util.List;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.shift_schedule.FugaShift;
import com.example.webapp.test_data.shift_schedule.HogeShift;
import com.example.webapp.test_data.shift_schedule.PiyoShift;

public class ShiftManagementTestData {
	
	private static LocalDate getNextMonthDate() {
	    return LocalDate.now().plusMonths(1);
	}

	private static int getNextMonthYear() {
	    return getNextMonthDate().getYear();
	}

	private static int getNextMonthValue() {
	    return getNextMonthDate().getMonthValue();
	}
	
	public static FullCalendarEntity getAnyEmployeeSchedule(EMPLOYEE name,Integer month,Integer date) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeShift(month,date);
		case EMPLOYEE.fuga:
			return new FugaShift(month,date);
		case EMPLOYEE.piyo:
			return new PiyoShift(month,date);
		default:
			break;
		}
		return null;
	}
	
	public static FullCalendarEntity getAnyEmployeeRequest(EMPLOYEE name,Integer year,Integer month,Integer date) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeShift(year,month,date);
		case EMPLOYEE.fuga:
			return new FugaShift(year,month,date);
		case EMPLOYEE.piyo:
			return new PiyoShift(year,month,date);
		default:
			break;
		}
		return null;
	}
	
//	getAnyEmployeeRequestと内容は一緒だが、「シフト希望」と「確定シフト」は意味が違うので分けている
	public static FullCalendarEntity getAnyEmployeeNextMonthSchedule(EMPLOYEE name,Integer year,Integer month,Integer date) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeShift(year,month,date);
		case EMPLOYEE.fuga:
			return new FugaShift(year,month,date);
		case EMPLOYEE.piyo:
			return new PiyoShift(year,month,date);
		default:
			break;
		}
		return null;
	}

	public static List<FullCalendarEntity> getAllSchedules(){
		return List.of(
				new HogeShift(4,1)
				,new FugaShift(4,1)
				,new HogeShift(5,1)
				,new PiyoShift(4,1)
				,new FugaShift(5,1)
				,new FugaShift(5,11)
				);
	}
	
	public static List<FullCalendarEntity> getAprilSchedules(){
		return List.of(
				new HogeShift(4,1)
				,new FugaShift(4,1)
				,new PiyoShift(4,1)
				);
	}
	
	public static List<FullCalendarEntity> getAllHogeRequests(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new HogeShift(year,month,1)
				,new HogeShift(year,month,15)
				,new HogeShift(year,month,28)
				);
	}
	
	public static List<FullCalendarEntity> getAllRequests(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new HogeShift(year,month,1)
				,new HogeShift(year,month,15)
				,new HogeShift(year,month,28)
				,new FugaShift(year,month,10)
				,new FugaShift(year,month,28)
				);
	}
	
	public static List<FullCalendarEntity> getPiyoRequests(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new PiyoShift(year,month,1)
				,new PiyoShift(year,month,15)
				);
	}
	
	public static List<FullCalendarEntity> getUpdatedHogeRequests(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new HogeShift(year,month,1)
				,new HogeShift(year,month,15)
				);
	}
	
	public static List<FullCalendarEntity> getDuplicatedPiyoRequests(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new PiyoShift(year,month,1)
				,new PiyoShift(year,month,1)
				);
	}
	
	public static List<FullCalendarEntity> getNextMonthScheduleToInsert(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new HogeShift(year,month,1)
				,new HogeShift(year,month,15)
				,new FugaShift(year,month,10)
				);
	}
	
	public static List<FullCalendarEntity> getUpdatedAprilSchedules(){
		return List.of(
				new HogeShift(4,1)
				,new FugaShift(4,1)
				);
	}
	
	public static List<FullCalendarEntity> getDuplicatedHogeScheduleToInsert(){
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return List.of(
				new HogeShift(year,month,1)
				,new HogeShift(year,month,1)
				);
	}
	
	public static String hogeRequestDateStringToDelete() {
		Integer year=getNextMonthYear();
		Integer month=getNextMonthValue();
		return LocalDate.of(year, month, 28).toString();
	}
	
}
