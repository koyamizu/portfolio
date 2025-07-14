package com.example.webapp.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.webapp.exception.IllegalDataManipulatingException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.repository.PasswordMapper;
import com.example.webapp.service.PasswordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

	private final PasswordMapper mapper;

	@Override
	public void updatePassword(Integer employeeId, String newBCryptPassword) throws IllegalDataManipulatingException, TooLongDataException {
		if(newBCryptPassword.length()!=60) {
			throw new IllegalDataManipulatingException("不正な値が代入されている可能性があります。管理者に問い合わせてください。");
		}
		try{
			mapper.updateByEmployeeId(employeeId, newBCryptPassword);
		}catch(DataIntegrityViolationException e) {
			throw new TooLongDataException("不正な値が代入されている可能性があります。管理者に問い合わせてください。");
		}
	}
}
