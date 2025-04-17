package com.example.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.webapp.entity.Shift;
import com.example.webapp.repository.TimeRecorderMapper;

import lombok.RequiredArgsConstructor;

@SpringBootApplication
@RequiredArgsConstructor
public class TimeRecorderAndShiftManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimeRecorderAndShiftManagementApplication.class, args)
		.getBean(TimeRecorderAndShiftManagementApplication.class).exe();
	}

	private final TimeRecorderMapper mapper;
	
	public void exe() {
		System.out.println("本日の出勤者");
		for(Shift row:mapper.selectTodaysEmployees()) {
			System.out.println(row);
		}
	}
}
