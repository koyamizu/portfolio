package com.example.webapp.test_data;

import com.example.webapp.entity.AbsenceReason;

import lombok.Data;

@Data
public class AbsenceReasonData {
	private AbsenceReason reason1;
	private AbsenceReason reason2;
	private AbsenceReason reason3;
	
	public AbsenceReasonData() {
		reason1=new AbsenceReason(1,"理由1");
		reason2=new AbsenceReason(2,"理由2");
		reason3=new AbsenceReason(3,"理由3");
	}
}
