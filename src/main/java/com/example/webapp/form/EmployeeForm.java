package com.example.webapp.form;

import com.example.webapp.entity.Role;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {
//	従業員id
	private Integer employeeId;
//	従業員パスワード
	private String password;
//	従業員名
	@NotNull(message="従業員名を入力してください")
	@Pattern(regexp = "^[\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}ー々]+$"
	,message="従業員名は漢字、カタカナ、ひらがなのみ使用できます")
	private String name;
//	生年月日
	@NotNull(message="生年月日を入力してください")
	@Pattern(regexp="^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$",
			message="生年月日はハイフン区切りで入力してください")
	private String birth;
//	電話番号
//	念のため、現在携帯電話番号として使われていない030,040を指定
	@NotNull(message="電話番号を入力してください")
	@Pattern(regexp="^0[34]0-[0-9]{4}-[0-9]{4}$",
			message="電話番号は030または040から始まり、「3桁-4桁-4桁」の形式で入力してください")
	private String tel;
//	住所
	@NotNull(message="住所を入力して下さい")
	@Pattern(regexp = "^[\\p{IsHan}]{2,3}[都|道|府|県]"
			+ "[\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}]{1,7}[市|区|町|村].+$"
	,message="住所を正しく入力してください")
	private String address;
	
	@NotNull(message="従業員区別を選んでください")
	private Role authority;
	
	private Boolean isNew;
}
