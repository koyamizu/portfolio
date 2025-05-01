package com.example.webapp.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User{

	public LoginUser(String employeeId,String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(employeeId,password,authorities);
	}
}
