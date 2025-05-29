package com.example.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//AbsenceReasonとかに変える
public class Absence {

	private Integer reasonId;
	
	//nameとかにするか
	private String reason;
}
