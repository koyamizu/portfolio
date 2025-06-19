package com.example.webapp.exception;

public class DuplicateEmployeeException extends Exception{
	
	public DuplicateEmployeeException() {
		super();
	}
	
	public DuplicateEmployeeException(String message) {
		super(message);
	}
}
