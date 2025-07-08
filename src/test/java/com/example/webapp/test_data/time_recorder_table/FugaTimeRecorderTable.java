package com.example.webapp.test_data.time_recorder_table;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.test_data.employee.Fuga;

public class FugaTimeRecorderTable extends ShiftSchedule{
	
	public FugaTimeRecorderTable() {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Fuga().getEmployeeIdAndName()
				,null
				);
	}
	
	public FugaTimeRecorderTable(Integer hour,Integer minute,Integer second) {
		super(
				null
				,LocalDate.now()
				,LocalTime.of(6,0,0)
				,LocalTime.of(9,0,0)
				,null
				,new Fuga().getEmployeeIdAndName()
				,new ClockTime(hour, minute,second)
				);
	}
	
	//FugaAbsenceApplicationの初期化用。Employeeの情報を含まない。
	public FugaTimeRecorderTable(LocalDate date) {
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
