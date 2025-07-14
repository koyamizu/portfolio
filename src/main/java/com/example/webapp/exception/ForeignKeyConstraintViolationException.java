package com.example.webapp.exception;

public class ForeignKeyConstraintViolationException extends Exception{
	private Integer employeeId;
	
	public ForeignKeyConstraintViolationException() {
		super();
	}
	
	public ForeignKeyConstraintViolationException(String message) {
		super(message);
	}
	
	public Integer getEmployeeId() {
		return this.employeeId;
	}
}
