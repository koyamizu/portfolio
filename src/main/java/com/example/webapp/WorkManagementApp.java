package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class WorkManagementApp {

	public static void main(String[] args) {
		SpringApplication.run(WorkManagementApp.class, args);
	}
}
