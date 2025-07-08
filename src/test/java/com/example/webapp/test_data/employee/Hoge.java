package com.example.webapp.test_data.employee;

import java.time.LocalDate;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.utility.PasswordGenerator;

public class Hoge extends Employee {
	
	public Hoge() {
		super(1001,PasswordGenerator.generatePassword("hogehoge"),"hoge"
				,LocalDate.of(1980,1,1),"090-1111-1111","福岡県福岡市東区テスト1-1",Role.ADMIN, null, null, null);
	}
}
