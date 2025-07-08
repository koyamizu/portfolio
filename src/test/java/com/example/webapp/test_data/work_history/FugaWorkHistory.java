package com.example.webapp.test_data.work_history;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.test_data.employee.Fuga;

//今回は勤務時間、履歴作成および更新時間は固定にしてある
public class FugaWorkHistory extends TimeRecord{

	public FugaWorkHistory(Integer month,Integer date,AbsenceReason reason) {
		super(
				LocalDate.of(2025, month, date)
				,new Fuga().getEmployeeId()
				,new Fuga().getName()
				,LocalTime.of(5, 59, 59)
				,LocalTime.of(9, 1, 1)
				,LocalDateTime.of(2025, 1,1,0,0)
				,LocalDateTime.of(2025, 12,31,0,0)
				,reason
				);
	}
}
