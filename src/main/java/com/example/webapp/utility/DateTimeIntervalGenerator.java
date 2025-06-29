package com.example.webapp.utility;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeIntervalGenerator {
	
	
	public static int getIntervalBetweenMidNight() {
		LocalDateTime now=LocalDateTime.now();
		LocalDate nextDay=LocalDate.now().plusDays(1);
		
		LocalDateTime midNight=LocalDateTime.of(nextDay, LocalTime.of(0, 0));
		return (int)Duration.between(now, midNight).getSeconds();
		
	}
}
