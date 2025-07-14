package com.example.webapp.test_data;

import java.util.List;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.absence_history.FugaAbsenceHistory;
import com.example.webapp.test_data.absence_history.HogeAbsenceHistory;
import com.example.webapp.test_data.absence_history.PiyoAbsenceHistory;
import com.example.webapp.test_data.absence_reason.ApproveNull;
import com.example.webapp.test_data.absence_reason.NotApproved;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.employee.FugaEmployeeIdAndName;
import com.example.webapp.test_data.employee.HogeEmployeeIdAndName;
import com.example.webapp.test_data.work_history.FugaWorkHistory;

public class WorkHistoryManagementTestDataGenerator {
	

	public static TimeRecord getFugaWorkHistory(Integer month,Integer day) {
		return new FugaWorkHistory(month,day);
	}
	
	public static TimeRecord getAnyEmployeeAbsenceHistory(EMPLOYEE name,Integer month,Integer day,AbsenceReason reason) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeAbsenceHistory(month,day,reason);
		case EMPLOYEE.fuga:
			return new FugaAbsenceHistory(month,day,reason);
		case EMPLOYEE.piyo:
			return new PiyoAbsenceHistory(month,day,reason);
		default:
			break;
		}
		return null;
	}
	
	public static List<TimeRecord> getAllWorkHistory(){
		return List.of(
				new HogeAbsenceHistory(4,1,new AbsenceReason(1,"理由1"))
				,new FugaAbsenceHistory(4,15,new NotApproved(1))
				,new PiyoAbsenceHistory(4,30,new ApproveNull(2))
//				,new HogeAbsenceHistory(5,1,new ReasonNull())
//				,new FugaWorkHistory(5,1)
//				,new FugaWorkHistory(5,11)
				);
	}
	
	public static List<TimeRecord> getFugaWorkHistoryOfMay(){
		return List.of(
				new FugaWorkHistory(5,1)
				,new FugaWorkHistory(5,11)
				);
	}
	
	public static List<Employee> getEmployeeIdAndNameWhoWorkedInMay(){
		return List.of(new HogeEmployeeIdAndName(),new FugaEmployeeIdAndName());
	}
	
//	public static String query_selectByEmployeeIdAndDate() {
//		return "SELECT\n"
//				+ "		  t.date\n"
//				+ "		  ,t.clock_in\n"
//				+ "		  ,t.clock_out\n"
//				+ "		  ,t.employee_id\n"
//				+ "		  ,e.name AS employee_name\n"
//				+ "		FROM\n"
//				+ "		  time_records AS t\n"
//				+ "		  INNER JOIN employees AS e\n"
//				+ "	 	  ON t.employee_id = e.id\n"
//				+ "		WHERE\n"
//				+ "		  t.date=?\n"
//				+ "		  AND t.employee_id=?\n"
//				+ "		;";
//	}
}
