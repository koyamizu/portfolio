package com.example.webapp.entity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * シフト提出の締切日を扱うクラス。第三金曜日を締め切り日としている。
 * 単体テストの際、他の月でも同様に機能するのかを確かめたかったので、
 * コンストラクタで初期化する際には引数に任意の日付を与えるようにしている。
 */
public class ShiftRequestDeadLine {

	private LocalDate deadLine;
//	private static LocalDate today;
//	static {
//		today=LocalDate.now();
//	}
	public ShiftRequestDeadLine(LocalDate date) {
		int thisYear=date.getYear();
		int thisMonth=date.getMonthValue();
		
		LocalDateTime firstDay=LocalDateTime.of(thisYear, thisMonth, 1, 0, 0);
		int weekDayOfFirstDay=firstDay.getDayOfWeek().getValue();
		int friday=DayOfWeek.FRIDAY.getValue();
		int firstFriday=1+((friday-weekDayOfFirstDay+7)%7);
		
		this.deadLine=LocalDate.of(thisYear, thisMonth, firstFriday+14);
	}
	
	@Override
	public String toString() {
		return deadLine.format(DateTimeFormatter.ofPattern("L月d日（E）"));
	}
	
	public boolean isOverDeadLine(LocalDate date) {
		return date.isAfter(deadLine);
	}
	
	public boolean isJustOrUnderDeadLine(LocalDate date) {
		return date.isBefore(deadLine)||date.isEqual(deadLine);
	}
}
