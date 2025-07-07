package com.example.webapp.test_data;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;

public class EmployeeTestData {
	
	private Employee hoge;
	private Employee fuga;
	private Employee piyo;
	private Employee foo;
	
	public EmployeeTestData() {
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		hoge=new Employee(1001,encoder.encode("hogehoge"),"hoge"
				,LocalDate.of(1980,1,1),"090-1111-1111","福岡県福岡市東区テスト1-1",Role.ADMIN, null, null, null);
		fuga=new Employee(1002,encoder.encode("fugafuga"),"fuga"
				,LocalDate.of(1990,1,1),"090-2222-2222","福岡県福岡市中央区テスト1-1",Role.USER, null, null, null);
		piyo=new Employee(1003,encoder.encode("piyopiyo"),"piyo"
				,LocalDate.of(2000,1,1),"090-3333-3333","福岡県福岡市南区テスト1-1",Role.USER, null, null, null);
		foo=new Employee(1004,encoder.encode("foofoo"),"foo"
				,LocalDate.of(1970,1,1),"090-4444-4444","福岡県福岡市早良区テスト1-1",Role.USER, null, null, null);
	}
	
	public Employee getExistingEmployeeHoge() {
		return hoge;
	}
	
	public Employee getExistingEmployeePiyo() {
		return piyo;
	}
	
	public Employee getNewEmployeeFoo() {
		return foo;
	}
	
	public Employee getPiyoEmployeeIdAndName() {
		Employee piyoIdAndName=new Employee();
		piyoIdAndName.setEmployeeId(piyo.getEmployeeId());
		piyoIdAndName.setName(piyo.getName());
		return piyoIdAndName;
	}
	
	public List<Employee> getAllEmployeeIdAndName(){
		Employee hogeIdAndName=new Employee();
		Employee fugaIdAndName=new Employee();
		Employee piyoIdAndName=new Employee();
		hogeIdAndName.setEmployeeId(hoge.getEmployeeId());
		hogeIdAndName.setName(hoge.getName());
		fugaIdAndName.setEmployeeId(fuga.getEmployeeId());
		fugaIdAndName.setName(fuga.getName());
		piyoIdAndName.setEmployeeId(piyo.getEmployeeId());
		piyoIdAndName.setName(piyo.getName());
		return List.of(hogeIdAndName,fugaIdAndName,piyoIdAndName);
	}
	
	public List<Employee> getAllEmployees(){
		return List.of(hoge,fuga,piyo);
	}
	
	public String getPassword() {
		return hoge.getPassword();
	}
}
