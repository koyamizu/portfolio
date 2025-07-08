package com.example.webapp.test_data.absence_history;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.employee.Hoge;

public class HogeAbsenceHistory extends TimeRecord {

	public HogeAbsenceHistory(Integer month,Integer date,AbsenceReason reason) {
		super(
				LocalDate.of(2025, month, date)
				,new Hoge().getEmployeeId()
				,new Hoge().getName()
				,null
				,null
				,null
				,null
				,reason
				);
	}
}
