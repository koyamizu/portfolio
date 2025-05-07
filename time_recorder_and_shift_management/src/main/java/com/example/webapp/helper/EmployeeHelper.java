package com.example.webapp.helper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;

public class EmployeeHelper {
	
	public static Employee convertEmployee(EmployeeForm form) {
		var employee=new Employee();
		employee.setId(form.getId());
		employee.setPassword(form.getPassword());
		employee.setName(form.getName());
		employee.setBirth(LocalDate.parse(form.getBirth(),DateTimeFormatter.ofPattern("yyyy-MM-dd")));;
		employee.setTel(form.getTel());
		employee.setAddress(form.getAddress());
		employee.setAuthority(form.getAuthority());
		return employee;
		}
	
	public static EmployeeForm convertEmployeeForm(Employee employee) {
		var form=new EmployeeForm();
		form.setId(employee.getId());
		form.setPassword(employee.getPassword());
		form.setName(employee.getName());
		form.setBirth(employee.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		form.setTel(employee.getTel());
		form.setAddress(employee.getAddress());
		form.setAuthority(employee.getAuthority());
		form.setIsNew(false);
		return form;
	}
}
