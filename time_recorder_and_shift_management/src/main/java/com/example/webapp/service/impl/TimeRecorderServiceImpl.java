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
	public ShiftAndTimestamp selectShiftAndTimestampByEmployeeIdAndDate(Integer id, LocalDate date) {
		return timeRecorderMapper.selectShiftAndTimestampByEmployeeIdAndDate(id, date);
	}

	@Override
	public ShiftAndTimestamp selectShiftAndTimestampByShiftId(Integer shift_id) {
		return timeRecorderMapper.selectShiftAndTimestampByShiftId(shift_id);
	}

	@Override
	public void start(Integer shift_id) {
		timeRecorderMapper.start(shift_id);
	}

	@Override
	public void end(Integer shift_id) {
		timeRecorderMapper.end(shift_id);
	}
}
