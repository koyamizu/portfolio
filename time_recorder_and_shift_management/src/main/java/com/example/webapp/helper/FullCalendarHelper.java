package com.example.webapp.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.form.FullCalendarForm;

public class FullCalendarHelper {

	public static void setColorProperties(String eventColor, String textColor, List<FullCalendarEntity> shifts) {
		shifts.stream().forEach(s -> s.setBackgroundColor(eventColor));
		shifts.stream().forEach(s -> s.setBorderColor(eventColor));
		shifts.stream().forEach(s -> s.setTextColor(textColor));
	}

	//	public static void setDisplayProperty(String display,List<FullCalendarEntity> shifts) {
	//		shifts.stream().forEach(s->s.setDisplay(display));		
	//	}
	//	
	//	public static void setProperties(String eventColor,String textColor,String display ,List<FullCalendarEntity> shifts) {
	//		shifts.stream().forEach(s->s.setBackgroundColor(eventColor));
	//		shifts.stream().forEach(s->s.setBorderColor(eventColor));
	//		shifts.stream().forEach(s->s.setTextColor(textColor));
	//		shifts.stream().forEach(s->s.setDisplay(display));
	//	}
	
	public static FullCalendarForm convertFullCalendarForm(FullCalendarEntity entity) {
		FullCalendarForm form = new FullCalendarForm();
		form.setId(entity.getId());
		form.setEmployeeId(entity.getEmployeeId());
		form.setStart(LocalDate.parse(entity.getStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		form.setScheduledStart(LocalTime.parse(entity.getScheduledStart(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		form.setScheduledEnd(LocalTime.parse(entity.getScheduledEnd(), DateTimeFormatter.ofPattern("HH:mm:ss")));
		return form;
	}
	
	public static FullCalendarEntity convertFullCalendarEntity(FullCalendarForm form) {
	    FullCalendarEntity entity = new FullCalendarEntity();
	    entity.setId(form.getId());
	    entity.setEmployeeId(form.getEmployeeId());
	    entity.setStart(form.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
	    entity.setScheduledStart(form.getScheduledStart().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    entity.setScheduledEnd(form.getScheduledEnd().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	    return entity;
	}

//	public static FullCalendarForm convertFullCalendarFormList(List<FullCalendarEntity> entities) {
//		FullCalendarForm form = new FullCalendarForm();
//		form.setId(e.getId());
//		form.setEmployeeId(e.getEmployeeId());
//		form.setStart(LocalDate.parse(e.getStart(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//		form.setScheduledStart(LocalTime.parse(e.getScheduledStart(), DateTimeFormatter.ofPattern("HH:mm:ss")));
//		form.setScheduledEnd(LocalTime.parse(e.getScheduledEnd(), DateTimeFormatter.ofPattern("HH:mm:ss")));
//		return form;
//	}
//	
//	public static FullCalendarEntity convertFullCalendarEntityList(List<FullCalendarForm> forms) {
//	    FullCalendarEntity entity = new FullCalendarEntity();
//	    entity.setId(f.getId());
//	    entity.setEmployeeId(f.getEmployeeId());
//	    entity.setStart(f.getStart().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
//	    entity.setScheduledStart(f.getScheduledStart().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    entity.setScheduledEnd(f.getScheduledEnd().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
//	    return entity;
//	}

}
