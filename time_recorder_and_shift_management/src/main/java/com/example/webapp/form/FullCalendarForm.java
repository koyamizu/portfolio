package com.example.webapp.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
//jsonにはシフト希望テーブルの主キー（shiftId）が含まれるが、jsonからShfitScheduleEditFormに変換する
//際には不要になる。変換したくない値があるときは、↓このアノテーションをつける
@JsonIgnoreProperties(ignoreUnknown=true)
public class FullCalendarForm {
	
	private Integer employeeId;
	//json->javaのクラスの変換の際、LocalDate型のフィールドにはこのアノテーションをつける必要がある
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate start;
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime scheduledStart;
	@JsonDeserialize(using = LocalTimeDeserializer.class)
	private LocalTime scheduledEnd;	
}
