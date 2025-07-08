package com.example.webapp.test_data.employee;

import java.time.LocalDate;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.utility.PasswordGenerator;

public class Foo extends Employee {
	
	public Foo() {
		super(1004,PasswordGenerator.generatePassword("foofoo"),"foo"
				,LocalDate.of(1970,1,1),"090-4444-4444","福岡県福岡市早良区テスト1-1",Role.USER, null, null, null);
	}
}
