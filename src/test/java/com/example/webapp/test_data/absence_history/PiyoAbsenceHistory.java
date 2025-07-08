package com.example.webapp.test_data.absence_history;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.employee.Piyo;

public class PiyoAbsenceHistory extends TimeRecord {

	public PiyoAbsenceHistory(Integer month,Integer date,AbsenceReason reason) {
		super(
				LocalDate.of(2025, month, date)
				,new Piyo().getEmployeeId()
				,new Piyo().getName()
				,null
				,null
				,null
				,null
				,reason
				);
	}
}
