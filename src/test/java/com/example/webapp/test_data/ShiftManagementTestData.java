package com.example.webapp.test_data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;

public class ShiftManagementTestData {
	
	private FullCalendarEntity schedule_hoge_0401;
	private FullCalendarEntity schedule_fuga_0401;
	private FullCalendarEntity schedule_piyo_0401;
	private FullCalendarEntity schedule_hoge_0501;
	private FullCalendarEntity schedule_fuga_0501;
	private FullCalendarEntity schedule_fuga_0511;
	
//	既存のrequest、または翌月のシフトとして使用
	private FullCalendarEntity request_hoge_01;
	private FullCalendarEntity request_hoge_15;
	private FullCalendarEntity request_hoge_28;
	private FullCalendarEntity request_fuga_10;
	private FullCalendarEntity request_fuga_20;
	
	//request挿入用
	private FullCalendarEntity request_piyo_01;
	private FullCalendarEntity request_piyo_15;
	
	LocalDate todayOfNextMonth=LocalDate.now().plusMonths(1);
	LocalDate firstDayOfNextMonth=LocalDate.of(todayOfNextMonth.getYear(),todayOfNextMonth.getMonthValue(),1);
	
	public ShiftManagementTestData() {
		var factory=new EmployeeTestData();
		List<Employee> e=factory.getAllEmployeeIdAndName();
		schedule_hoge_0401=new FullCalendarEntity(1,e.get(0),LocalDate.of(2025,4,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		schedule_fuga_0401=new FullCalendarEntity(2,e.get(1),LocalDate.of(2025,4,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		schedule_piyo_0401=new FullCalendarEntity(3,e.get(2),LocalDate.of(2025,4,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		schedule_hoge_0501=new FullCalendarEntity(4,e.get(0),LocalDate.of(2025,5,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		schedule_fuga_0501=new FullCalendarEntity(5,e.get(1),LocalDate.of(2025,5,1),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		schedule_fuga_0511=new FullCalendarEntity(6,e.get(1),LocalDate.of(2025,5,11),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		
		request_hoge_01=new FullCalendarEntity(1,e.get(0),firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		request_hoge_15=new FullCalendarEntity(2,e.get(0),firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		request_hoge_28=new FullCalendarEntity(3,e.get(0),firstDayOfNextMonth.plusDays(27),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		request_fuga_10=new FullCalendarEntity(4,e.get(1),firstDayOfNextMonth.plusDays(9),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		request_fuga_20=new FullCalendarEntity(5,e.get(1),firstDayOfNextMonth.plusDays(19),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		
		request_piyo_01=new FullCalendarEntity(6,e.get(2),firstDayOfNextMonth,LocalTime.of(6,0,0),LocalTime.of(9,0,0));
		request_piyo_15=new FullCalendarEntity(7,e.get(2),firstDayOfNextMonth.plusDays(14),LocalTime.of(6,0,0),LocalTime.of(9,0,0));
	}
	
	public FullCalendarEntity getHogeSchedule0401() {
		return schedule_hoge_0401;
	}
	
	public List<FullCalendarEntity> getAllSchedules(){
		return List.of(
				schedule_hoge_0401
				,schedule_fuga_0401
				,schedule_hoge_0501
				,schedule_piyo_0401
				,schedule_fuga_0501
				,schedule_fuga_0511
				);
	}
	
	public List<FullCalendarEntity> getAprilSchedules(){
		return List.of(
				schedule_hoge_0401
				,schedule_fuga_0401
				,schedule_piyo_0401
				);
	}
	
	public List<FullCalendarEntity> getAllHogeRequestsOnlyEmployeeId(){
		var hogeOnlyId=new Employee();
		hogeOnlyId.setEmployeeId(1001);
		request_hoge_01.setEmployee(hogeOnlyId);
		request_hoge_15.setEmployee(hogeOnlyId);
		request_hoge_28.setEmployee(hogeOnlyId);
		return List.of(
				request_hoge_01
				,request_hoge_15
				,request_hoge_28
				);
	}
	
	public List<FullCalendarEntity> getAllRequests(){
		return List.of(
				request_hoge_01
				,request_hoge_15
				,request_hoge_28
				,request_fuga_10
				,request_fuga_20
				);
	}
	
	public List<FullCalendarEntity> getPiyoRequests(){
		return List.of(
				request_piyo_01
				,request_piyo_15
				);
	}
	
	public List<FullCalendarEntity> getUpdatedHogeRequests(){
		return List.of(
				request_hoge_01
				,request_hoge_15
				);
	}
	
	public List<FullCalendarEntity> getDuplicatedPiyoRequests(){
		return List.of(
				request_piyo_01
				,request_piyo_01
				);
	}
	
	public List<FullCalendarEntity> getNextMonthScheduleToInsert(){
		return List.of(
				request_hoge_01
				,request_hoge_15
				,request_fuga_10
				);
	}
	
	public List<FullCalendarEntity> getUpdatedAprilSchedules(){
		return List.of(
				schedule_hoge_0401
				,schedule_fuga_0401
				);
	}
	
	public List<FullCalendarEntity> getDuplicatedHogeScheduleToInsert(){
		return List.of(
				request_hoge_01
				,request_hoge_01
				);
	}
	
	public String hogeRequestDateStringToDelete() {
		return request_hoge_28.getStart().toString();
	}
	
}
