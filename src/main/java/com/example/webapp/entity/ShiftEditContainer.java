package com.example.webapp.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiftEditContainer{
	private List<FullCalendarDisplay> shifts;
	private List<Employee> allEmployees;
}
