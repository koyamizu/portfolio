package com.example.webapp.service.impl;

import java.util.Collections;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.LoginUser;

@Service
public class LoginUserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
		if (employeeId.equals("1001")) {
			return new LoginUser("1001", "togo", Collections.emptyList());
		}
		if (employeeId.equals("1002")) {
			return new LoginUser("1002", "yoshizuka", Collections.emptyList());
		}
		if (employeeId.equals("1003")) {
			return new LoginUser("1003", "koga", Collections.emptyList());
		}
		if (employeeId.equals("1004")) {
			return new LoginUser("1004", "chihaya", Collections.emptyList());
		}
		throw new UsernameNotFoundException(
				employeeId + "=>指定しているIDをもつ従業員は存在しません");
	}
}
