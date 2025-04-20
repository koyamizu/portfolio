package com.example.webapp.helper;

import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;

public class EmployeeHelper {
	
	public static Employee convertEmployee(EmployeeForm form) {
		var employee=new Employee();
		employee.setId(form.getId());
		employee.setPassword(form.getPassword());
		employee.setName(form.getName());
		employee.setTel(form.getTel());
		employee.setAddress(form.getAddress());
		return employee;
		}
	
	public static EmployeeForm convertEmployeeForm(Employee employee) {
		var form=new EmployeeForm();
		form.setId(employee.getId());
		form.setPassword(employee.getPassword());
		form.setName(employee.getName());
		form.setTel(employee.getTel());
		form.setAddress(employee.getAddress());
		form.setIsNew(false);
		return form;
	}
}
