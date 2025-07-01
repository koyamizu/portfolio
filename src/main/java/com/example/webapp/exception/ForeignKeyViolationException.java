package com.example.webapp.exception;

public class ForeignKeyViolationException extends Exception{
	private Integer employeeId;
	
	public ForeignKeyViolationException() {
		super();
	}
	
	public ForeignKeyViolationException(String message) {
		super(message);
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}
}
