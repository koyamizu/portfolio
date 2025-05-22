package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TimeRecorderAndShiftManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeRecorderAndShiftManagementApplication.class, args);
	}
}
