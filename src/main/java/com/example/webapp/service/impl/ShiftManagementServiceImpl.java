package com.example.webapp.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftCreateContainer;
import com.example.webapp.entity.ShiftEditContainer;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateShiftException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.FullCalendarForm;
import com.example.webapp.helper.FullCalendarHelper;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.repository.ShiftManagementMapper;
import com.example.webapp.service.ShiftManagementService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ShiftManagementServiceImpl implements ShiftManagementService {

	private final ShiftManagementMapper shiftManagementMapper;
	private final EmployeesManagementMapper employeesManagementMapper;

	@Override
	public List<FullCalendarEntity> getThreeMonthShifts(Integer targetMonth) {
		return shiftManagementMapper.selectThreeMonthByTargetMonth(targetMonth);
	}

	@Override
	public List<FullCalendarEntity> getPersonalShiftRequests(Integer employeeId) {
		return shiftManagementMapper.selectByEmployeeId(employeeId);
	}

	@Override
	public List<ShiftSchedule> getAllShiftsAfterToday(Integer employeeId) {
		return shiftManagementMapper.selectAllAfterTodayByEmployeeId(employeeId);
	}
	
	@Override
	public List<FullCalendarEntity> getOneMonthShifts(Integer targetMonth) {
		return shiftManagementMapper.selectOneMonthByTargetMonth(targetMonth);
	}

	@Override
	public void registerShiftRequests(String requestsStr, Integer employeeId)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> requests = mapper.readValue(requestsStr,
				new TypeReference<List<FullCalendarForm>>() {
				});
		shiftManagementMapper.insertRequest(requests);
	}

	@Override
	@Transactional
	public void updateShiftRequests(String requestsStr, Integer employeeId)
			throws JsonMappingException, JsonProcessingException, DuplicateShiftException {

		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> latestVersionForm = mapper.readValue(requestsStr,
				new TypeReference<List<FullCalendarForm>>() {
				});

		List<FullCalendarEntity> oldVersionStr = shiftManagementMapper.selectByEmployeeId(employeeId);
		List<FullCalendarForm> oldVersionForm = oldVersionStr.stream()
				.map(o -> FullCalendarHelper.convertFullCalendarForm(o)).toList();

		List<FullCalendarForm> additionals = latestVersionForm.stream()
				//				新しく追加した希望日
				.filter(r -> Objects.equals(r.getId(), null)
						//						かつ、すでに登録してある日付と重複していない
						&& oldVersionForm.stream().noneMatch(o -> r.getStart().isEqual(o.getStart())))
				.toList();
		if (!CollectionUtils.isEmpty(additionals)) {
			//			追加する日付が存在する
			try {
				shiftManagementMapper.insertRequest(additionals);
			} catch (DataIntegrityViolationException e) {
				//通常は起こりえない例外。ユーザーのミスではなくプログラム側のミス。
				throw new DuplicateShiftException("シフト日が重複して登録されています");
			}
		}
		//newRequestsに存在しない日付を削除
		shiftManagementMapper.deleteByEmployeeId(latestVersionForm, employeeId);

	}

	@Override
	public ShiftCreateContainer initializeShiftCreateContainerFields() {
		List<Employee> allEmployees = employeesManagementMapper.selectAllIdAndName();
		List<Employee> notSubmits = shiftManagementMapper.selectNotSubmit();
		List<FullCalendarEntity> requests = shiftManagementMapper.selectAll();
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", requests);
		return new ShiftCreateContainer(requests, allEmployees, notSubmits);
	}

	@Override
	public ShiftEditContainer initializeShiftEditContainerFields(Integer month) {
		List<Employee> allEmployees = employeesManagementMapper.selectAllIdAndName();
		List<FullCalendarEntity> shifts = shiftManagementMapper.selectOneMonthByTargetMonth(month);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", shifts);
		return new ShiftEditContainer(shifts, allEmployees);
	}

	@Override
	public void createNextMonthShifts(String newShiftsStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> newShifts = mapper.readValue(newShiftsStr,
				new TypeReference<List<FullCalendarForm>>() {
				});

		shiftManagementMapper.insertShift(newShifts);
	}

	@Override
	@Transactional
	public void updateShiftSchedules(String latestVersionStr, Integer month)
			throws JsonMappingException, JsonProcessingException, InvalidEditException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> latestVersion = mapper.readValue(latestVersionStr,
				new TypeReference<List<FullCalendarForm>>() {
				});
		List<FullCalendarForm> additionals = latestVersion.stream().filter(r -> Objects.equals(r.getId(), null))
				.toList();
		//追加なしで「更新」を押したときは迂回する。
		try {
			if (!CollectionUtils.isEmpty(additionals)) {
				shiftManagementMapper.insertShift(additionals);
			}
			shiftManagementMapper.deleteByMonth(latestVersion, month);
		} catch (DataIntegrityViolationException e) {
			throw new InvalidEditException("勤怠履歴のあるシフト/欠勤申請の出されているシフトは編集できません");
		}
	}

}
