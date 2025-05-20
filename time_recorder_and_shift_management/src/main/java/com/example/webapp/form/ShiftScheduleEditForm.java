package com.example.webapp.form;

//データの受け渡しをしているだけなので、entityにするかもしれない
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class ShiftScheduleEditForm {
	
	private Integer employeeId;
	//出勤日
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate start;
}
