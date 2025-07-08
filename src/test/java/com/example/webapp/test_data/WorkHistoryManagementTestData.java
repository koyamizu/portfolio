package com.example.webapp.test_data;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.absence_history.FugaAbsenceHistory;
import com.example.webapp.test_data.absence_history.HogeAbsenceHistory;
import com.example.webapp.test_data.absence_history.PiyoAbsenceHistory;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.work_history.FugaWorkHistory;

public class WorkHistoryManagementTestData {
	

	public static TimeRecord getFugaWorkHistory(Integer month,Integer date,AbsenceReason reason) {
		return new FugaWorkHistory(month,date,reason);
	}
	
	public static TimeRecord getAnyEmployeeWorkHistory(EMPLOYEE name,Integer month,Integer date,AbsenceReason reason) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeAbsenceHistory(month,date,reason);
		case EMPLOYEE.fuga:
			return new FugaAbsenceHistory(month,date,reason);
		case EMPLOYEE.piyo:
			return new PiyoAbsenceHistory(month,date,reason);
		default:
			break;
		}
		return null;
	}
	
	public String selectByEmployeeIdAndDate() {
		return "SELECT\n"
				+ "		  t.date\n"
				+ "		  ,t.clock_in\n"
				+ "		  ,t.clock_out\n"
				+ "		  ,t.employee_id\n"
				+ "		  ,e.name AS employee_name\n"
				+ "		FROM\n"
				+ "		  time_records AS t\n"
				+ "		  INNER JOIN employees AS e\n"
				+ "	 	  ON t.employee_id = e.id\n"
				+ "		WHERE\n"
				+ "		  t.date=?\n"
				+ "		  AND t.employee_id=?\n"
				+ "		;";
	}
}
