package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.form.TimeRecordForm;
import com.example.webapp.helper.TimeRecordHelper;
import com.example.webapp.repository.WorkHistoryManagementMapper;
import com.example.webapp.service.WorkHistoryManagementService;
import com.google.common.base.Objects;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WorkHistoryManagementServiceImpl implements WorkHistoryManagementService {

	private final WorkHistoryManagementMapper mapper;
	@Override
	public List<TimeRecord> getAllWorkHistoriesToDate(Integer targetMonth) {
		return mapper.selectAllByMonth(targetMonth);
	}
	
	@Override
	public List<TimeRecord> getPersonalWorkHistoriesToDateByEmployeeIdAndMonth(Integer employeeId,Integer targetMonth){
		return mapper.selectByEmployeeIdAndMonth(employeeId,targetMonth);
	}
	
	@Override
	public List<Employee> getWorkedEmployeesByMonth(Integer targerMonth){
		return mapper.selectEmployeeByMonth(targerMonth);
	}
	
	@Override
	public TimeRecordForm getWorkHistoryDetailByEmployeeIdAndDate(Integer employeeId, LocalDate date) throws NoDataFoundException {
		TimeRecord targetHistory=mapper.selectByEmployeeIdAndDate(employeeId, date);
		if(Objects.equal(null, targetHistory)) {
			throw new NoDataFoundException("勤怠履歴データが取得できませんでした。管理者にお問い合わせください。");
		}
		return TimeRecordHelper.convertTimeRecordForm(targetHistory);
	}
	
	@Override
	public void updateWorkHistory(TimeRecordForm form) throws InvalidEditException {
		TimeRecord updatedHistory = TimeRecordHelper.convertTimeRecord(form);
		LocalTime clockIn=updatedHistory.getClockIn();
		LocalTime clockOut=updatedHistory.getClockOut();
		Boolean isInvalid=clockIn.isAfter(clockOut);
		if(isInvalid) {
			throw new InvalidEditException("出勤時刻を退勤時刻の後には設定できません");
		}
		mapper.update(updatedHistory);
	}
}
