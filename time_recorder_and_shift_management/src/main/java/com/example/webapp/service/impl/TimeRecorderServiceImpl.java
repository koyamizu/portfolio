package com.example.webapp.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.repository.TimeRecorderMapper;
import com.example.webapp.service.TimeRecorderService;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TimeRecorderServiceImpl implements TimeRecorderService {

	private final TimeRecorderMapper timeRecorderMapper;

	@Override
	public List<ShiftSchedule> selectEmployeesByDate(LocalDate date) {
		return timeRecorderMapper.selectEmployeeByDate(date);
	}

	@Override
	public ShiftSchedule selectByEmployeeId(Integer employeeId) {
		return timeRecorderMapper.selectByEmployeeId(employeeId);
	}

	@Override
	public ShiftSchedule selectByShiftId(Integer shiftId) {
		return timeRecorderMapper.selectByShiftId(shiftId);
	}

	@Override
	public void updateStartTimeByShiftId(Integer shiftId) {
		timeRecorderMapper.insert(shiftId);
	}

	@Override
	public void updateEndTimeByShiftId(Integer shiftId) {
		timeRecorderMapper.updateEndTimeByShiftId(shiftId);
	}
}
