package com.example.webapp.test_data.time_recorder_table;

import java.time.LocalTime;

import com.example.webapp.entity.TimeRecord;

public class ClockTime extends TimeRecord {

	public ClockTime(Integer hour,Integer minute,Integer second) {
		super(null, null, null, LocalTime.of(hour,minute, second), null, null, null, null);
	}
}
