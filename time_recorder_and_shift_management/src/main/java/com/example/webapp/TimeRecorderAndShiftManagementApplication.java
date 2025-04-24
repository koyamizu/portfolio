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
	//	public static void main(String[] args) {
	//		SpringApplication.run(TimeRecorderAndShiftManagementApplication.class, args)
	//		.getBean(TimeRecorderAndShiftManagementApplication.class).exe();
	//	}
	//
	//	private final TimeRecorderService service;
	//	public void exe() {
	//		System.out.println("本日の出勤者");
	//		for(ShiftAndTimestamp row:service.selectEmployeesByDate(LocalDate.now())) {
	//			System.out.println(row);
	//		}
	//		
	//		System.out.println("出勤");
	//		ShiftAndTimestamp togo=service.selectShiftAndTimestampByEmployeeIdAndDate("093001", LocalDate.now());
	//		service.start(togo);
	//		
	//		System.out.println("確認");
	//		for(ShiftAndTimestamp row:service.selectEmployeesByDate(LocalDate.now())) {
	//			System.out.println(row);
	//		}
	//	}
}
