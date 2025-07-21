package com.example.webapp.test_data.authority;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.webapp.entity.Role;

public class AuthorityList {
	
	public static  List<GrantedAuthority> getAuthoritiesOfADMIN() {
		return List.of(
				new SimpleGrantedAuthority(Role.ADMIN.name())
						,new SimpleGrantedAuthority(Role.USER.name()));
	}
	
	public static  List<GrantedAuthority> getAuthoritiesOfUSER() {
		return List.of(new SimpleGrantedAuthority(Role.USER.name()));
	}
}
