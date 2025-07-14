package com.example.webapp.service;

import com.example.webapp.exception.IllegalDataManipulatingException;
import com.example.webapp.exception.TooLongDataException;

public interface PasswordService {
	void updatePassword(Integer employeeId,String newPassword) throws IllegalDataManipulatingException, TooLongDataException;
}
