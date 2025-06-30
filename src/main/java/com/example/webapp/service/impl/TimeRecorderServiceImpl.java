package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.entity.TimeRecord;
import com.example.webapp.exception.DuplicateClockException;
import com.example.webapp.exception.InvalidClockException;
import com.example.webapp.exception.NoDataException;
import com.example.webapp.repository.TimeRecorderMapper;
import com.example.webapp.service.TimeRecorderService;
import com.google.common.base.Objects;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeRecorderServiceImpl implements TimeRecorderService {

	private final TimeRecorderMapper timeRecorderMapper;

	@Override
	public List<ShiftSchedule> getEmployeeWithClockTime(LocalDate date) throws NoDataException {

		List<ShiftSchedule> todayEmployeeList = timeRecorderMapper.selectByDate(date);

		if (CollectionUtils.isEmpty(todayEmployeeList)) {
//			TimeRecorderMapper.xmlに問題があるのでBadSqlGrammarExceptionを投げる
			throw new BadSqlGrammarException(null, null, null);
		}

		return todayEmployeeList;
	}

	@Override
	public Employee getEmployeeToClock(List<ShiftSchedule> todayMembersWithClockTime,Integer employeeId)
			throws NoDataException {
//		↓これsessionからEmployees取り出してその中から打刻するメンバーをemployeeIdで取り出せばいいから不要な気がする
		List<Employee> employees = todayMembersWithClockTime.stream().map(ShiftSchedule::getEmployee).toList();
		Employee employeeToClock = employees.stream().filter(e -> e.getEmployeeId().equals(employeeId))
			    .findFirst().orElse(null);
		if (Objects.equal(employeeToClock, null)) {
			throw new NoDataException("その従業員IDの方は本日出勤予定ではありません");
		}
		return employeeToClock;
	}


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
		TimeRecord targetTimeRecord = timeRecorderMapper.selectTodayTimeRecordByEmployeeId(employeeId);
		if (Objects.equal(targetTimeRecord, null)) {
			throw new InvalidClockException("「出勤」より先に「退勤」は押せません");
		}
		if (!Objects.equal(targetTimeRecord.getClockOut(), null)) {
			throw new DuplicateClockException("すでに退勤済みです");
		}
		timeRecorderMapper.update(employeeId);
	}
}
