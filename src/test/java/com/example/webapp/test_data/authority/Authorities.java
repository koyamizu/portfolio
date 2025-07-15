package com.example.webapp.test_data.authority;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.webapp.entity.Role;

public class Authorities {
	
	public static  Collection<? extends GrantedAuthority> getADMIN() {
		return List.of(
				new SimpleGrantedAuthority(Role.ADMIN.name())
						,new SimpleGrantedAuthority(Role.USER.name()));
	}
	
	public static  Collection<? extends GrantedAuthority> getUSER() {
		return List.of(new SimpleGrantedAuthority(Role.USER.name()));
	}
}
