package com.example.webapp.test_data.absence_applicaiton;

import java.time.LocalDate;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.test_data.employee.Hoge;
import com.example.webapp.test_data.time_recorder_table.HogeTimeRecorderTable;

public class HogeAbsenceApplication extends AbsenceApplication {

	public HogeAbsenceApplication(LocalDate date,AbsenceReason reason,String detail) {
		super(null
				,new HogeTimeRecorderTable(date)
				,new Hoge().getEmployeeIdAndName()
				,reason
				,detail
				,null
				,null
				,null
				);
	}
	
	public HogeAbsenceApplication(LocalDate date,AbsenceReason reason,String detail,Boolean isApproved) {
		super(null
				,new HogeTimeRecorderTable(date)
				,new Hoge().getEmployeeIdAndName()
				,reason
				,detail
				,isApproved
				,null
				,null
				);
	}
}
