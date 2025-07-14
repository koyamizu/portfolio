package com.example.webapp.test_data.absence_applicaiton;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.test_data.employee.FugaEmployeeIdAndName;
import com.example.webapp.test_data.time_recorder_table.FugaTimeRecorderTable;

public class FugaAbsenceApplication extends AbsenceApplication {

	public FugaAbsenceApplication(LocalDate date,AbsenceReason reason,String detail) {
		super(null
				,new FugaTimeRecorderTable(date)
				,new FugaEmployeeIdAndName()
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
				,new FugaEmployeeIdAndName()
				,reason
				,detail
				,isApproved
				,null
				,null
				);
	}
}
