package com.example.webapp.test_data.shift_schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.test_data.employee.FugaEmployeeIdAndName;

public class FugaShift extends FullCalendarEntity {

	public FugaShift(Integer month, Integer day) {
		super(null, new FugaEmployeeIdAndName(), LocalDate.of(2025,month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
	
	public FugaShift(Integer year, Integer month, Integer day) {
		super(null, new FugaEmployeeIdAndName(), LocalDate.of(year, month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
}