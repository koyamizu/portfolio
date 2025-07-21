package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.impl.LoginUserDetailsServiceImpl;
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.login.AdminMock;
import com.example.webapp.test_data.login.UserMock;

@ExtendWith(MockitoExtension.class)
public class LoginUserServiceImplTest {

	@InjectMocks
	LoginUserDetailsServiceImpl service;
	
	@Mock
	EmployeesManagementMapper mapper;
	
	@Test
	void test_loadUserByUsername_ADMIN() {
		Employee dummy=EmployeesManagementTestDataGenerator.getEmployeeIdAndName(EMPLOYEE.hoge);
		dummy.setPassword("password");
		dummy.setAuthority(Role.ADMIN);
		doReturn(dummy).when(mapper).selectById(dummy.getEmployeeId());
			
		UserDetails admin=new AdminMock();
		UserDetails user=new UserMock();
		UserDetails actual=service.loadUserByUsername(Integer.toString(1001));
		assertThat(actual.getAuthorities()).isEqualTo(admin.getAuthorities());
		assertThat(actual.getAuthorities()).isNotEqualTo(user.getAuthorities());
	}
	
	@Test
	void test_loadUserByUsername_USER() {
		Employee dummy=EmployeesManagementTestDataGenerator.getEmployeeIdAndName(EMPLOYEE.hoge);
		dummy.setPassword("password");
		dummy.setAuthority(Role.USER);
		doReturn(dummy).when(mapper).selectById(dummy.getEmployeeId());
		
		UserDetails admin=new AdminMock();
		UserDetails user=new UserMock();
		UserDetails actual=service.loadUserByUsername(Integer.toString(1001));
		assertThat(actual.getAuthorities()).isNotEqualTo(admin.getAuthorities());
		assertThat(actual.getAuthorities()).isEqualTo(user.getAuthorities());
	}
	
	@Test
	void test_loadUserByUsername_throws_UsernameNotFoundException() {
		doReturn(null).when(mapper).selectById(9999);
		assertThrows(UsernameNotFoundException.class,()->service.loadUserByUsername(Integer.toString(9999)));
	}
	
	void test_loadUserByUsername_throws_NullPointerException() {
		Employee dummy=EmployeesManagementTestDataGenerator.getEmployeeIdAndName(EMPLOYEE.hoge);
//		dummy.setPassword("password"); passwordをnullにする
		dummy.setAuthority(Role.USER);
		doReturn(dummy).when(mapper).selectById(dummy.getEmployeeId());
		
		assertThrows(NullPointerException.class,()->service.loadUserByUsername(Integer.toString(1001)));
		
	}
}
