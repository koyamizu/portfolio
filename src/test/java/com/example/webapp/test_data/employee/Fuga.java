package com.example.webapp.test_data.employee;

import java.time.LocalDate;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.utility.PasswordGenerator;

public class Fuga extends Employee {

	public Fuga() {
		super(1002,PasswordGenerator.generatePassword("fugafuga"),"fuga"
				,LocalDate.of(1990,1,1),"090-2222-2222","福岡県福岡市中央区テスト1-1",Role.USER, null, null, null);
	}
}
