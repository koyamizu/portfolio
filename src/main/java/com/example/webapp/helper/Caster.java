package com.example.webapp.helper;

import java.util.List;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.entity.ShiftSchedule;

public class Caster {

	
	@SuppressWarnings("unchecked")
	public static List<ShiftSchedule> castToShiftScheduleList(Object rawCollection) {
	    return (List<ShiftSchedule>)rawCollection;
	}

	@SuppressWarnings("unchecked")
	public static List<AbsenceApplication> castToAbsenceApplicationList(Object rawCollection) {
	    return (List<AbsenceApplication>)rawCollection;
	}
	
	@SuppressWarnings("unchecked")
	public static List<FullCalendarEntity> castToFullCalendarEntityList(Object rawCollection) {
		return (List<FullCalendarEntity>)rawCollection;
	}
}
