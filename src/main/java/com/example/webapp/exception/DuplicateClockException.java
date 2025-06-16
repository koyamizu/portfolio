package com.example.webapp.exception;

public class DuplicateClockException extends Exception {

	public DuplicateClockException() {
		super();
	}
	
	public DuplicateClockException(String message) {
		super(message);
	}
}
