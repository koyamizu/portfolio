package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.ShiftAndTimestamp;
import com.example.webapp.repository.TimeRecorderMapper;
import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeRecorderServiceImpl implements TimeRecorderService {

	private final TimeRecorderMapper timeRecorderMapper;

	@Override
	public List<ShiftAndTimestamp> selectEmployeesByDate(LocalDate date) {
		return timeRecorderMapper.selectEmployeesByDate(date);
	}

	@Override
	public ShiftAndTimestamp selectShiftByEmployeeIdAndDate(Integer employeeId, LocalDate date) {
		return timeRecorderMapper.selectShiftByEmployeeIdAndDate(employeeId, date);
	}

	@Override
	public ShiftAndTimestamp selectTimestampByShiftId(Integer shiftId) {
		return timeRecorderMapper.selectTimestampByShiftId(shiftId);
	}

	@Override
	public void updateStartTimeByShiftId(Integer shiftId) {
		timeRecorderMapper.updateStartTimeByShiftId(shiftId);
	}

	@Override
	public void updateEndTimeByShiftId(Integer shiftId) {
		timeRecorderMapper.updateEndTimeByShiftId(shiftId);
	}
}
