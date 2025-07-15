package com.example.webapp.test_data.login;

import com.example.webapp.entity.LoginUser;
import com.example.webapp.test_data.authority.Authorities;

public class User extends LoginUser {

	public User() {
		super(Integer.toString(1001),"hogehoge",Authorities.getUSER(),"hoge");
	}
}
