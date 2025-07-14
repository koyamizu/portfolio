package com.example.webapp.test_data.shift_schedule;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.test_data.employee.PiyoEmployeeIdAndName;

public class PiyoShift extends FullCalendarEntity {
	
	public PiyoShift(Integer month, Integer day) {
		super(null, new PiyoEmployeeIdAndName(), LocalDate.of(2025, month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
	
	public PiyoShift(Integer year, Integer month, Integer day) {
		super(null, new PiyoEmployeeIdAndName(), LocalDate.of(year, month, day),
				LocalTime.of(6, 0, 0), LocalTime.of(9, 0, 0));
	}
}
