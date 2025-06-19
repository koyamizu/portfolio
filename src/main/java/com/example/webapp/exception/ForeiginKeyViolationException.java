package com.example.webapp.exception;

public class ForeiginKeyViolationException extends Exception{
	
	public ForeiginKeyViolationException() {
		super();
	}
	
	public ForeiginKeyViolationException(String message) {
		super(message);
	}
}
