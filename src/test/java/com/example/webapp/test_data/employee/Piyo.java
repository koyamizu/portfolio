package com.example.webapp.test_data.employee;

import java.time.LocalDate;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.utility.PasswordGenerator;

public class Piyo extends Employee {

	public Piyo() {
		super(1003,PasswordGenerator.generatePassword("piyopiyo"),"piyo"
				,LocalDate.of(2000,1,1),"090-3333-3333","福岡県福岡市南区テスト1-1",Role.USER, null, null, null);
	}
}
