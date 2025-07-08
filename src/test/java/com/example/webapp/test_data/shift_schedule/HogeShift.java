package com.example.webapp.test_data.shift_schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.test_data.employee.Hoge;

public class HogeShift extends FullCalendarEntity {

	
	public HogeShift(Integer month, Integer day) {
		super(null, new Hoge().getEmployeeIdAndName(), LocalDate.of(2025, month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
	
	public HogeShift(Integer year, Integer month, Integer day) {
		super(null, new Hoge().getEmployeeIdAndName(), LocalDate.of(year, month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
}
