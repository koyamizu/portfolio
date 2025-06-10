package com.example.webapp.common;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;

import lombok.Data;

@Data
public class EmployeeTestData {

	public Employee getYoshizuka() {
		return new Employee(
				1001, "yoshizuka01", "吉塚", LocalDate.parse("1986-05-27"), "030-1920-8394", "福岡県福岡市博多区吉塚本町13-28",
				Role.ADMIN, null, null, null);
	}

	public Employee getChihaya() {
		return new Employee(
				null, new BCryptPasswordEncoder().encode("chihaya03"), "千早", LocalDate.parse("1998-01-09"),
				"030-1974-4571", "福岡県福岡市東区千早", Role.USER, null, null, null);
	}

	public List<Employee> createTestEmployeeIdAndName() {
		return List.of(
				new Employee(1001, "yoshizuka01", null, null, null, null, null, null, null, null),
				new Employee(1002, "koga09", null, null, null, null, null, null, null, null),
				new Employee(1003, "kurosaki21", null, null, null, null, null, null, null, null),
				new Employee(1004, "togo13", null, null, null, null, null, null, null, null),
				new Employee(1005, "komorie30", null, null, null, null, null, null, null, null),
				new Employee(1006, "hakozaki02", null, null, null, null, null, null, null, null));
	}

	public List<Employee> createAllEmployees() {
		return List.of(
				new Employee(1001, "yoshizuka01", "吉塚", LocalDate.parse("1986-05-27"), "030-1920-8394",
						"福岡県福岡市博多区吉塚本町13-28", Role.ADMIN, null, null, null),
				new Employee(1002, "koga09", "古賀", LocalDate.parse("1972-01-06"), "030-5438-1393", "福岡県古賀市天神1-1-1",
						Role.USER, null, null, null),
				new Employee(1003, "kurosaki21", "黒崎", LocalDate.parse("1980-07-12"), "030-6703-8331",
						"福岡県北九州市八幡西区黒崎3-15-1", Role.USER, null, null, null),
				new Employee(1004, "togo13", "東郷", LocalDate.parse("1992-10-11"), "030-4962-6497", "福岡県宗像市田熊4-9-1",
						Role.USER, null, null, null),
				new Employee(1005, "komorie30", "小森江", LocalDate.parse("1998-03-30"), "030-9821-2306",
						"福岡県北九州市門司区小森江3-11", Role.USER, null, null, null),
				new Employee(1006, "hakozaki02", "箱崎", LocalDate.parse("2005-04-01"), "030-7319-1564", "福岡県福岡市東区筥松2-32",
						Role.USER, null, null, null));
	}

}
