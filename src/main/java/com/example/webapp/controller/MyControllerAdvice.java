package com.example.webapp.controller;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.TypeMismatchDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.webapp.exception.ForeignKeyConstraintViolationException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class MyControllerAdvice {

	@ExceptionHandler({DataAccessResourceFailureException.class,BadSqlGrammarException.class
		,EmptyResultDataAccessException.class,TypeMismatchDataAccessException.class
		,NullPointerException.class,DataIntegrityViolationException.class,ForeignKeyConstraintViolationException.class})
	public String showDatabaseErrorPage(Exception e) {
		e.printStackTrace();
		return "error/system-error";
	}
}
