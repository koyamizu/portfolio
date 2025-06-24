package com.example.webapp.exception;

public class DuplicateApplicationException extends Exception {
	public DuplicateApplicationException() {
		super();
	}
	
	public DuplicateApplicationException(String msg) {
		super(msg);
	}
}
