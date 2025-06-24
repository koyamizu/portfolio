package com.example.webapp.helper;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.webapp.entity.FullCalendarDisplay;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.form.FullCalendarForm;

public class FullCalendarHelper {

	public static void setColorProperties(String eventColor, String textColor, List<FullCalendarDisplay> shifts) {
		shifts.stream().forEach(s -> s.setBackgroundColor(eventColor));
		shifts.stream().forEach(s -> s.setBorderColor(eventColor));
		shifts.stream().forEach(s -> s.setTextColor(textColor));
	}
	
//	public static FullCalendarForm convertFullCalendarForm(FullCalendarEntity entity) {
//		FullCalendarEntity form = new FullCalendarEntity();
//		form.setId(entity.getId());
//		form.setEmployeeId(entity.getEmployeeId());
//		form.setStart(LocalDate.parse(entity.getStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		form.setScheduledStart(LocalTime.parse(entity.getScheduledStart(), DateTimeFormatter.ofPattern("HH:mm:ss")));
//		form.setScheduledEnd(LocalTime.parse(entity.getScheduledEnd(), DateTimeFormatter.ofPattern("HH:mm:ss")));
//		return form;
//	}
	
	public static FullCalendarEntity convertFullCalendarEntity(FullCalendarForm form) {
	    FullCalendarEntity entity = new FullCalendarEntity();
	    entity.setShiftId(form.getId());
	    entity.getEmployee().setEmployeeId(form.getEmployeeId());
		entity.setStart(entity.getStart());
		entity.setScheduledStart(entity.getScheduledStart());
		entity.setScheduledEnd(entity.getScheduledEnd());
	    return entity;
	}

	public static FullCalendarDisplay convertFullCalendarDisplay(FullCalendarEntity entity) {
		FullCalendarDisplay display = new FullCalendarDisplay();
		display.setId(entity.getShiftId());
		display.setEmployeeId(entity.getEmployee().getEmployeeId());
		display.setTitle(entity.getEmployee().getName());
		display.setStart(entity.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		display.setScheduledStart(entity.getScheduledStart().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		display.setScheduledEnd(entity.getScheduledEnd().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		return display;
	}
	
//	public static FullCalendarEntity convertFullCalendarEntity(FullCalendarDisplay form) {
//	    FullCalendarEntity entity = new FullCalendarEntity();
//	    entity.setId(form.getId());
//	    entity.setEmployeeId(form.getEmployeeId());
//	    entity.setStart(form.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//	    entity.setScheduledStart(form.getScheduledStart().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    entity.setScheduledEnd(form.getScheduledEnd().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    return entity;
//	}

}
