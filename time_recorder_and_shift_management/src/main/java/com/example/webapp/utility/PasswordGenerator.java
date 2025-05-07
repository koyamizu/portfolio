package com.example.webapp.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
//	public static void main(String[] args) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		String[] rawPasswords = {"yoshizuka01","koga09","kurosaki21","togo13","komorie30","hakozaki02"};
//		for(String rw:rawPasswords) {
//			System.out.println(encoder.encode(rw));			
//		}
//	}
	public static String generatePassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}
}
