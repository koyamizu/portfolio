package com.example.webapp.test_data.absence_applicaiton;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.test_data.employee.Fuga;
import com.example.webapp.test_data.time_recorder_table.FugaTimeRecorderTable;

public class FugaAbsenceApplication extends AbsenceApplication {

	public FugaAbsenceApplication(LocalDate date,AbsenceReason reason,String detail) {
		super(null
				,new FugaTimeRecorderTable(date)
				,new Fuga().getEmployeeIdAndName()
				,reason
				,detail
				,null
				,null
				,null
				);
	}
	
	public FugaAbsenceApplication(LocalDate date,AbsenceReason reason,String detail,Boolean isApproved) {
		super(null
				,new FugaTimeRecorderTable(date)
				,new Fuga().getEmployeeIdAndName()
				,reason
				,detail
				,isApproved
				,null
				,null
				);
	}
}
