package com.example.webapp.exception;

public class EmployeeDataIntegrityViolationException extends Exception {
	private Integer employeeId;
	
	public EmployeeDataIntegrityViolationException() {
		super();
	}
	
	public EmployeeDataIntegrityViolationException(String message,Integer employeeId) {
		super(message);
		this.employeeId=employeeId;
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}
}
