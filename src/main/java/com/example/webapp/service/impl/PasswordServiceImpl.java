package com.example.webapp.service.impl;

import org.springframework.stereotype.Service;

import com.example.webapp.repository.PasswordMapper;
import com.example.webapp.service.PasswordService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

	private final PasswordMapper mapper;
	@Override
	public void updatePasswordByEmployeeId(Integer employeeId, String newPassword) {
		mapper.updateByEmployeeId(employeeId, newPassword);
	}

}
