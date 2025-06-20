package com.example.webapp.exception;

public class DuplicateShiftException extends Exception {

	public DuplicateShiftException() {
		super();
	}
	
	public DuplicateShiftException(String message) {
		super(message);
	}
}
