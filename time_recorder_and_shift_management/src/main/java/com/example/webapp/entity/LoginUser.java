package com.example.webapp.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//UserDetails実装クラス
public class LoginUser extends User{
	
	private String name;

	public LoginUser(String employeeId,String password,
			Collection<? extends GrantedAuthority> authorities,String name) {
		super(employeeId,password,authorities);
		this.name=name;
	}
	
	public String getName() {
		return name;
	}
}
