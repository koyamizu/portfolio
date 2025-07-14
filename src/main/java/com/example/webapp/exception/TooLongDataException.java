package com.example.webapp.exception;

public class TooLongDataException extends Exception {
	public TooLongDataException() {
		super();
	}
	
	public TooLongDataException(String msg) {
		super(msg);
	}
}
