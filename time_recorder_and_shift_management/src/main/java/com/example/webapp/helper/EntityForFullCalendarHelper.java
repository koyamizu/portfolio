package com.example.webapp.helper;

import java.util.List;

import com.example.webapp.entity.FullCalendarEntity;

public class EntityForFullCalendarHelper {

	public static void setColorProperties(String eventColor,String textColor,List<FullCalendarEntity> shifts) {
		shifts.stream().forEach(s->s.setBackgroundColor(eventColor));
		shifts.stream().forEach(s->s.setBorderColor(eventColor));
		shifts.stream().forEach(s->s.setTextColor(textColor));
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
}
