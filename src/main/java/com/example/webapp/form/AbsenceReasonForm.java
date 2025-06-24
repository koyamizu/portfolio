package com.example.webapp.form;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//AbsenceReasonとかに変える
public class AbsenceReasonForm {

	@NotNull(message="欠勤理由を選択してください")
	private Integer reasonId;
	
	//nameとかにするか
	private String name;
}
