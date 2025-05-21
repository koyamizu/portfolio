package com.example.webapp.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	
	public static String generatePassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}
}
