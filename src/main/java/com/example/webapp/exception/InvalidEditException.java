package com.example.webapp.exception;

public class InvalidEditException extends RuntimeException {

	public InvalidEditException() {
		super();
	}
	
	public InvalidEditException(String message) {
		super(message);
	}
}
