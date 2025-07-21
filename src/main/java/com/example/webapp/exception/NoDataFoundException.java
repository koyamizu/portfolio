package com.example.webapp.exception;

public class NoDataFoundException extends Exception{
	
	public NoDataFoundException() {
		super();
	}
	
	public NoDataFoundException(String message) {
		super(message);
	}
}
