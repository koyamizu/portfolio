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
	public ShiftAndTimestamp selectShiftAndTimestampByEmployeeIdAndDate(String id, LocalDate date) {
		return timeRecorderMapper.selectShiftAndTimestampByEmployeeIdAndDate(id, date);
	}

	@Override
	public void start(ShiftAndTimestamp shift) {
		timeRecorderMapper.start(shift);
	}

	@Override
	public void end(ShiftAndTimestamp shift) {
		timeRecorderMapper.end(shift);
	}

}
