package com.example.webapp.exception;

public class ForeignKeyConstraintViolationException extends Exception{
	
	public ForeignKeyConstraintViolationException() {
		super();
	}
	
	public ForeignKeyConstraintViolationException(String message) {
		super(message);
	}
	
}
