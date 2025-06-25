package com.example.webapp.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShiftCreateContainer{
	private List<FullCalendarDisplay> requests;
	private List<Employee> allEmployees;
	private List<Employee> notSubmits;
}
