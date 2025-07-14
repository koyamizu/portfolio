package com.example.webapp.test_data;

import java.util.List;

import com.example.webapp.entity.Employee;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.employee.Foo;
import com.example.webapp.test_data.employee.Fuga;
import com.example.webapp.test_data.employee.FugaEmployeeIdAndName;
import com.example.webapp.test_data.employee.Hoge;
import com.example.webapp.test_data.employee.HogeEmployeeIdAndName;
import com.example.webapp.test_data.employee.Piyo;
import com.example.webapp.test_data.employee.PiyoEmployeeIdAndName;

public class EmployeesManagementTestDataGenerator {

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
	
	public static Employee getEmployeeIdAndName(EMPLOYEE name) {
		switch (name) {
		case EMPLOYEE.hoge:
			return new HogeEmployeeIdAndName();
		case EMPLOYEE.fuga:
			return new FugaEmployeeIdAndName();
		case EMPLOYEE.piyo:
			return new PiyoEmployeeIdAndName();
		default:
			break;
		}
		return null;
	}

	public static List<Employee> getAllEmployeeIdAndName() {
		Employee hogeIdAndName = new HogeEmployeeIdAndName();
		Employee fugaIdAndName = new FugaEmployeeIdAndName();
		Employee piyoIdAndName = new PiyoEmployeeIdAndName();
		return List.of(hogeIdAndName, fugaIdAndName, piyoIdAndName);
	}

	public static List<Employee> getAllEmployees() {
		return List.of(new Hoge(), new Fuga(), new Piyo());
	}
	
//	public static String query_selectById() {
//		return "SELECT id,password, name, birth, tel, address, authority FROM employees WHERE id= ?;";
//	}
}
