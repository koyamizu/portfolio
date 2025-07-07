package com.example.webapp.utility;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {

	public static String generatePassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}

	public static void generate() {
		List<String> pass = List.of(generatePassword("hogehoge"), generatePassword("fugafuga"),
				generatePassword("piyopiyo"));

		for (String p : pass) {
			System.out.println(p);
		}
	}
}
