package com.example.webapp.test_data;

import java.util.List;

import com.example.webapp.entity.AbsenceReason;

import lombok.Data;

@Data
public class AbsenceReasonTestDataGenerator {
	
	public static List<AbsenceReason> getAllReasons(){
		return List.of(new AbsenceReason(1,"理由1")
				,new AbsenceReason(2,"理由2")
				,new AbsenceReason(3,"理由3")
				);
	}
}
