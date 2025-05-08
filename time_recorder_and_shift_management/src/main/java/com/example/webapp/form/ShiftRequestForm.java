package com.example.webapp.form;

import java.util.List;

import com.example.webapp.entity.EntityForFullCalendar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftRequestForm {
	
	private List<EntityForFullCalendar> requests;
	
	private Boolean isNew;
}
