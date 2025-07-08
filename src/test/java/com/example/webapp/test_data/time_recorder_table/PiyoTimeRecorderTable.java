package com.example.webapp.test_data.time_recorder_table;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.test_data.employee.Piyo;

public class PiyoTimeRecorderTable extends ShiftSchedule{
	
	public PiyoTimeRecorderTable() {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Piyo().getEmployeeIdAndName()
				,null
				);
	}
	
	public PiyoTimeRecorderTable(Integer hour,Integer minute,Integer second) {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Piyo().getEmployeeIdAndName()
				,new ClockTime(hour, minute,second)
				);
	}
	
	//PiyoAbsenceApplicationの初期化用。Employeeの情報を含まない。
	public PiyoTimeRecorderTable(LocalDate date) {
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
