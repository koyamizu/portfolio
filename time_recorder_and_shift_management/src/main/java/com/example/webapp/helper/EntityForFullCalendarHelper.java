package com.example.webapp.helper;

import java.util.List;

import com.example.webapp.entity.EntityForFullCalendar;

public class EntityForFullCalendarHelper {

	public static void setColorProperties(String eventColor,String textColor, List<EntityForFullCalendar> shifts) {
		shifts.stream().forEach(s->s.setBackgroundColor(eventColor));
		shifts.stream().forEach(s->s.setBorderColor(eventColor));
		shifts.stream().forEach(s->s.setTextColor(textColor));
	}
}
