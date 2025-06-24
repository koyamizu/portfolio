package com.example.webapp.exception;

public class InvalidAccessException extends Exception {
	public InvalidAccessException(){
		super();
	}
	
	public InvalidAccessException(String msg) {
		super(msg);
	}
}
