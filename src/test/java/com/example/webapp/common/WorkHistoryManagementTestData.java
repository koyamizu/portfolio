package com.example.webapp.common;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;

public class WorkHistoryManagementTestData {

	public TimeRecord getEmployeeId_1001_Date_0401_AbsenceReason_1_Approved() {
		TimeRecord expected = new TimeRecord();
		AbsenceReason reason = new AbsenceReason();
		reason.setName("理由1");
		expected.setDate(LocalDate.of(2025, 4, 1));
		expected.setEmployeeId(1001);
		expected.setEmployeeName("hogehoge");
		expected.setAbsenceReason(reason);
		return expected;
	}
	
	public TimeRecord getEmployeeId_1002_Date_0401_AbsenceReason_1_NotApproved() {
		TimeRecord expected = new TimeRecord();
		AbsenceReason reason = new AbsenceReason();
		reason.setName("未承認");
		expected.setDate(LocalDate.of(2025, 4, 1));
		expected.setEmployeeId(1002);
		expected.setEmployeeName("fugafuga");
		expected.setAbsenceReason(reason);
		return expected;
	}
	
	public TimeRecord getEmployeeId_1003_Date_0401_AbsenceReason_2_IsApprovedEqualsNull() {
		TimeRecord expected = new TimeRecord();
		AbsenceReason reason = new AbsenceReason();
		reason.setName("承認忘れ");
		expected.setDate(LocalDate.of(2025, 4, 1));
		expected.setEmployeeId(1003);
		expected.setEmployeeName("piyopiyo");
		expected.setAbsenceReason(reason);
		return expected;
	}
	
	public TimeRecord getEmployeeId_1001_Date_0501_AbsenceReasonIsNull() {
		TimeRecord expected = new TimeRecord();
		AbsenceReason reason = new AbsenceReason();
		reason.setName("不明");
		expected.setDate(LocalDate.of(2025, 5, 1));
		expected.setEmployeeId(1001);
		expected.setEmployeeName("hogehoge");
		expected.setAbsenceReason(reason);
		return expected;
	}
	
	public TimeRecord getEmployeeId_1002_Date_0501_worked() {
		TimeRecord expected = new TimeRecord();
		LocalTime clockIn = LocalTime.of(5, 59, 59);
		LocalTime clockOut = LocalTime.of(9, 1, 1);
		Duration dur = Duration.between(clockIn, clockOut);
		LocalTime workTime = LocalTime.of(dur.toHoursPart(), dur.toMinutesPart(), dur.toSecondsPart());
		AbsenceReason reason = new AbsenceReason();
		reason.setName(" ");
		expected.setDate(LocalDate.of(2025, 5, 1));
		expected.setEmployeeId(1002);
		expected.setEmployeeName("fugafuga");
		expected.setClockIn(clockIn);
		expected.setClockOut(clockOut);
		expected.setWorkTime(workTime);
		expected.setAbsenceReason(reason);
		expected.setCreatedAt(LocalDateTime.of(2025,1,1,0,0,0));
		expected.setUpdatedAt(LocalDateTime.of(2025,12,31,0,0,0));
		return expected;
	}
	
	public TimeRecord getEmployeeId_1002_Date_0511_worked() {
		TimeRecord expected = new TimeRecord();
		LocalTime clockIn = LocalTime.of(5, 59, 59);
		LocalTime clockOut = LocalTime.of(9, 1, 1);
		expected.setDate(LocalDate.of(2025, 5, 11));
		expected.setEmployeeId(1002);
		expected.setEmployeeName("fugafuga");
		expected.setClockIn(clockIn);
		expected.setClockOut(clockOut);
		return expected;
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
