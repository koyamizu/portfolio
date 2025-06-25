package com.example.webapp.helper;

import java.time.format.DateTimeFormatter;
import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.FullCalendarDisplay;
import com.example.webapp.entity.FullCalendarEntity;
import com.example.webapp.form.FullCalendarForm;

public class FullCalendarHelper {

	public static void setColorProperties(String eventColor, String textColor, List<FullCalendarDisplay> shifts) {
		shifts.stream().forEach(s -> s.setBackgroundColor(eventColor));
		shifts.stream().forEach(s -> s.setBorderColor(eventColor));
		shifts.stream().forEach(s -> s.setTextColor(textColor));
	}
	
	public static FullCalendarForm convertFullCalendarForm(FullCalendarEntity entity) {
		FullCalendarForm form = new FullCalendarForm();
		form.setId(entity.getShiftId());
		form.setEmployeeId(entity.getEmployee().getEmployeeId());
		form.setStart(entity.getStart());
		form.setScheduledStart(entity.getScheduledStart());
		form.setScheduledEnd(entity.getScheduledEnd());
		return form;
	}
	
	public static FullCalendarEntity convertFullCalendarEntity(FullCalendarForm form) {
	    FullCalendarEntity entity = new FullCalendarEntity();
	    Employee employee=new Employee();
	    employee.setEmployeeId(form.getEmployeeId());
	    entity.setShiftId(form.getId());
	    entity.setEmployee(employee);
		entity.setStart(form.getStart());
		entity.setScheduledStart(form.getScheduledStart());
		entity.setScheduledEnd(form.getScheduledEnd());
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
	
	public static List<FullCalendarForm> convertFullCalendarForm(List<FullCalendarEntity> entities) {
		return entities.stream().map(e->convertFullCalendarForm(e)).toList();
	}
	
	public static List<FullCalendarEntity> convertFullCalendarEntity(List<FullCalendarForm> forms){
		return forms.stream().map(e->convertFullCalendarEntity(e)).toList();
	}
	
	public static List<FullCalendarDisplay> convertFullCalendarDisplay(List<FullCalendarEntity> entities){
		return entities.stream().map(e->convertFullCalendarDisplay(e)).toList();
	}

}
