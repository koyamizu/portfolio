package com.example.webapp.test_data.absence_history;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.employee.Fuga;

public class FugaAbsenceHistory extends TimeRecord {

	public FugaAbsenceHistory(Integer month,Integer date,AbsenceReason reason) {
		super(
				LocalDate.of(2025, month, date)
				,new Fuga().getEmployeeId()
				,new Fuga().getName()
				,null
				,null
				,null
				,null
				,reason
				);
	}
}
