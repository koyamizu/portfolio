package com.example.webapp.exception;

public class InvalidEmployeeIdException extends Exception{
	
	public InvalidEmployeeIdException() {
		super();
	}
	
	public InvalidEmployeeIdException(String message) {
		super(message);
	}
}
