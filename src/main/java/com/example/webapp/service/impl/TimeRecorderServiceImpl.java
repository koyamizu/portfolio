package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.TimeRecorderMapper;
import com.example.webapp.service.TimeRecorderService;
import com.google.common.base.Objects;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeRecorderServiceImpl implements TimeRecorderService {

	private final TimeRecorderMapper timeRecorderMapper;
	private final EmployeesManagementMapper employeesManagementMapper;

	@Override
	public List<ShiftSchedule> getEmployeeList(LocalDate date) throws NoDataException {

		List<ShiftSchedule> todayEmployeeList = timeRecorderMapper.selectByDate(date);

		if (CollectionUtils.isEmpty(todayEmployeeList)) {
			throw new NoDataException("今日の出勤者情報が取得できませんでした。");
		}

		return todayEmployeeList;
	}

	@Override
	public ShiftSchedule getTodayPersonalShiftData(Integer employeeId)
			throws InvalidEmployeeIdException, NoDataException {
		Employee targetEmployee = employeesManagementMapper.selectById(employeeId);
		if (Objects.equal(targetEmployee, null)) {
			throw new InvalidEmployeeIdException("そのIDを持つ従業員は存在しません");
		}
		ShiftSchedule targetShift = timeRecorderMapper.selectShiftScheduleByEmployeeId(employeeId);
		if (Objects.equal(targetShift, null)) {
			throw new NoDataException("その従業員IDの方は本日出勤予定ではありません");
		}
		return targetShift;
	}

//	@Override
//	public TimeRecord getTodayPersonalTimeRecordData(Integer employeeId) {
//		return timeRecorderMapper.selectTimeRecordByEmployeeId(employeeId);
//
//	}

	@Override
	public void clockIn(Integer employeeId) throws DuplicateClockException {
		try {
			timeRecorderMapper.insert(employeeId);
		} catch (DuplicateKeyException e) {
			throw new DuplicateClockException("すでに出勤済みです");
		}
	}

	@Override
	public void clockOut(Integer employeeId) throws InvalidClockException, DuplicateClockException {

		TimeRecord targetTimeRecord = timeRecorderMapper.selectTimeRecordByEmployeeId(employeeId);
		if (Objects.equal(targetTimeRecord, null)) {
			throw new InvalidClockException("「出勤」より先に「退勤」は押せません");
		}
		if (!Objects.equal(targetTimeRecord.getClockOut(), null)) {
			throw new DuplicateClockException("すでに退勤済みです");
		}
		timeRecorderMapper.update(employeeId);
	}
}
