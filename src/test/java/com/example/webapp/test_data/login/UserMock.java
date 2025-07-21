package com.example.webapp.test_data.login;

import com.example.webapp.entity.LoginUser;
import com.example.webapp.test_data.authority.AuthorityList;

public class UserMock extends LoginUser {

	public UserMock() {
		super(Integer.toString(1001),"hogehoge",AuthorityList.getAuthoritiesOfUSER(),"hoge");
	}
}
