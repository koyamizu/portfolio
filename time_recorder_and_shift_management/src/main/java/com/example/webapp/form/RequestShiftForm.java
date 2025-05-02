package com.example.webapp.form;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestShiftForm {

	private Integer employee_id;
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private List<LocalDate> shifts;
}
