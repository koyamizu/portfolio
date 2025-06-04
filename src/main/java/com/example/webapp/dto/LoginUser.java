package com.example.webapp.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//UserDetails実装クラス
public class LoginUser extends User{
	
	private String name;

	//プロパティは「username」じゃないとダメ系？
	public LoginUser(String employeeId,String password,
			Collection<? extends GrantedAuthority> authorities,String name) {
		super(employeeId,password,authorities);
		this.name=name;
	}
	
	public String getEmployeeId() {
		return getUsername();
	}
	public String getName() {
		return name;
	}
}
