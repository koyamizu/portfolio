package com.example.webapp.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

//UserDetails実装クラス
public class LoginUser extends User{
	
	private String name;

	//プロパティは「username」じゃないとダメ系？
	public LoginUser(String id,String password,
			Collection<? extends GrantedAuthority> authorities,String name) {
		super(id,password,authorities);
		this.name=name;
	}
	
	public String getId() {
		return getUsername();
	}
	public String getName() {
		return name;
	}
}
