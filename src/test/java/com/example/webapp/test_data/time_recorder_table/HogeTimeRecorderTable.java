package com.example.webapp.test_data.time_recorder_table;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.test_data.employee.Hoge;

public class HogeTimeRecorderTable extends ShiftSchedule{
	
	public HogeTimeRecorderTable() {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Hoge().getEmployeeIdAndName()
				,null
				);
	}
	
	public HogeTimeRecorderTable(Integer hour,Integer minute,Integer second) {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Hoge().getEmployeeIdAndName()
				,new ClockTime(hour, minute,second)
				);
	}
	
	//HogeAbsenceApplicationの初期化用。Employeeの情報を含まない。
	public HogeTimeRecorderTable(LocalDate date) {
		super(
				null
				,date
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,null
				,null
				);
	}
}
