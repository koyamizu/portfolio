package com.example.webapp.test_data;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.employee.Foo;
import com.example.webapp.test_data.employee.Fuga;
import com.example.webapp.test_data.employee.Hoge;
import com.example.webapp.test_data.employee.Piyo;

public class EmployeeTestData {

	public static Employee getEmployee(EMPLOYEE name) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new Hoge();
		case EMPLOYEE.fuga:
			return new Fuga();
		case EMPLOYEE.piyo:
			return new Piyo();
		case EMPLOYEE.foo:
			return new Foo();
		default:
			break;
		}
		return null;
	}

	public static List<Employee> getAllEmployeeIdAndName() {
		Employee hogeIdAndName = new Hoge().getEmployeeIdAndName();
		Employee fugaIdAndName = new Fuga().getEmployeeIdAndName();
		Employee piyoIdAndName = new Piyo().getEmployeeIdAndName();
		return List.of(hogeIdAndName, fugaIdAndName, piyoIdAndName);
	}

	public static List<Employee> getAllEmployees() {
		return List.of(new Hoge(), new Fuga(), new Piyo());
	}
}
