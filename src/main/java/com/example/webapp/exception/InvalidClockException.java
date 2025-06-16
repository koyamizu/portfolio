package com.example.webapp.exception;

public class InvalidClockException extends Exception {

	public InvalidClockException() {
		super();
	}
	
	public InvalidClockException(String message) {
		super(message);
	}
}
