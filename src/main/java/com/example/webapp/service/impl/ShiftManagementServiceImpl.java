package com.example.webapp.service.impl;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarDisplay;
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
	public List<FullCalendarDisplay> getThreeMonthShifts(Integer targetMonth) {
		List<FullCalendarEntity> entities=shiftManagementMapper.selectThreeMonthByTargetMonth(targetMonth);
//		return entities.stream().map(e->FullCalendarHelper.convertFullCalendarDisplay(e)).toList();
		return FullCalendarHelper.convertFullCalendarDisplay(entities);
	}

	@Override
	public List<FullCalendarDisplay> getPersonalShiftRequests(Integer employeeId) {
		List<FullCalendarEntity> entities=shiftManagementMapper.selectByEmployeeId(employeeId);
//		return entities.stream().map(e->FullCalendarHelper.convertFullCalendarDisplay(e)).toList();
		return FullCalendarHelper.convertFullCalendarDisplay(entities);
	}

	@Override
	public List<ShiftSchedule> getAllShiftsAfterToday(Integer employeeId) {
		return shiftManagementMapper.selectAllAfterTodayByEmployeeId(employeeId);
	}
	
	@Override
	public List<FullCalendarDisplay> getOneMonthShifts(Integer targetMonth) {
		List<FullCalendarEntity> entities=shiftManagementMapper.selectOneMonthByTargetMonth(targetMonth);
//		return entities.stream().map(e->FullCalendarHelper.convertFullCalendarDisplay(e)).toList();
		return FullCalendarHelper.convertFullCalendarDisplay(entities);
	}

	@Override
	public void registerShiftRequests(String requestsStr, Integer employeeId)
			throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> requestsForm = mapper.readValue(requestsStr,
				new TypeReference<List<FullCalendarForm>>() {
				});
		List<FullCalendarEntity> requests=FullCalendarHelper.convertFullCalendarEntity(requestsForm);
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
		List<FullCalendarEntity> latestVersion=FullCalendarHelper.convertFullCalendarEntity(latestVersionForm);

		List<FullCalendarEntity> oldVersion = shiftManagementMapper.selectByEmployeeId(employeeId);
//		List<FullCalendarForm> oldVersionForm = oldVersion.stream()
//				.map(o -> FullCalendarHelper.convertFullCalendarForm(o)).toList();

		List<FullCalendarEntity> additionals= latestVersion.stream()
				//				新しく追加した希望日
				.filter(l -> Objects.equals(l.getShiftId(), null)
						//						かつ、すでに登録してある日付と重複していない
						&& oldVersion.stream().noneMatch(o -> l.getStart().isEqual(o.getStart())))
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
		shiftManagementMapper.deleteByEmployeeId(latestVersion, employeeId);

	}

	@Override
	public ShiftCreateContainer initializeShiftCreateContainerFields() {
		List<Employee> allEmployees = employeesManagementMapper.selectAllIdAndName();
		List<Employee> notSubmits = shiftManagementMapper.selectNotSubmit();
		List<FullCalendarEntity> requests = shiftManagementMapper.selectAll();
		List<FullCalendarDisplay> requestsDisplay=FullCalendarHelper.convertFullCalendarDisplay(requests);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", requestsDisplay);
		return new ShiftCreateContainer(requestsDisplay, allEmployees, notSubmits);
	}

	@Override
	public ShiftEditContainer initializeShiftEditContainerFields(Integer month) {
		List<Employee> allEmployees = employeesManagementMapper.selectAllIdAndName();
		List<FullCalendarEntity> shifts = shiftManagementMapper.selectOneMonthByTargetMonth(month);
		List<FullCalendarDisplay> shiftsDisplay=FullCalendarHelper.convertFullCalendarDisplay(shifts);
		FullCalendarHelper.setColorProperties("#02e09a", "#006666", shiftsDisplay);
		return new ShiftEditContainer(shiftsDisplay, allEmployees);
	}

	@Override
	public void createNextMonthShifts(String newShiftsStr) throws JsonMappingException, JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> newShiftsForm = mapper.readValue(newShiftsStr,
				new TypeReference<List<FullCalendarForm>>() {
				});
		List<FullCalendarEntity> newShifts=FullCalendarHelper.convertFullCalendarEntity(newShiftsForm);
		shiftManagementMapper.insertShift(newShifts);
	}

	@Override
	@Transactional
	public void updateShiftSchedules(String latestVersionStr, Integer month)
			throws JsonMappingException, JsonProcessingException, InvalidEditException {
		ObjectMapper mapper = new ObjectMapper();
		List<FullCalendarForm> latestVersionForm = mapper.readValue(latestVersionStr,
				new TypeReference<List<FullCalendarForm>>() {
				});
		List<FullCalendarEntity> latestVersion=FullCalendarHelper.convertFullCalendarEntity(latestVersionForm);
		List<FullCalendarEntity> additionals = latestVersion.stream().filter(r -> Objects.equals(r.getShiftId(), null))
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
