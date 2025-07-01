package com.example.webapp.exception;

public class EmployeeDataIntegrityException extends Exception {
	private Integer employeeId;
	
	public EmployeeDataIntegrityException() {
		super();
	}
	
	public EmployeeDataIntegrityException(String message,Integer employeeId) {
		super(message);
		this.employeeId=employeeId;
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}
}
