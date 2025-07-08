package com.example.webapp.test_data;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.time_recorder_table.FugaTimeRecorderTable;
import com.example.webapp.test_data.time_recorder_table.HogeTimeRecorderTable;
import com.example.webapp.test_data.time_recorder_table.PiyoTimeRecorderTable;

public class TimeRecordTestData {
	
	public static ShiftSchedule getAnyEmployeeTimeRecorderTable(EMPLOYEE name) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeTimeRecorderTable();
		case EMPLOYEE.fuga:
			return new FugaTimeRecorderTable();
		case EMPLOYEE.piyo:
			return new PiyoTimeRecorderTable();
		default:
			break;
		}
		return null;
	}
	
	public static ShiftSchedule getAnyEmployeeTimeRecorderTable(EMPLOYEE name,Integer hour,Integer minute,Integer second) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeTimeRecorderTable(hour,minute,second);
		case EMPLOYEE.fuga:
			return new FugaTimeRecorderTable(hour,minute,second);
		case EMPLOYEE.piyo:
			return new PiyoTimeRecorderTable(hour,minute,second);
		default:
			break;
		}
		return null;
	}
}
