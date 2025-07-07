package com.example.webapp.test_data;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;

public class AbsenceApplicationTestData {
	
	private AbsenceApplication hoge_today_reason1_approve_is_null;
	private AbsenceApplication fuga_today_reason2_approved;
	private AbsenceApplication hoge_tomorrow_reason3_not_approved;
	
	public AbsenceApplicationTestData() {
		var ar=new AbsenceReason();
		hoge_today_reason1_approve_is_null=new AbsenceApplication();
	}
}
