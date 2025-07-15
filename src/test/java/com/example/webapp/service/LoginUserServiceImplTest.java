package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.impl.LoginUserDetailsServiceImpl;
import com.example.webapp.test_data.EmployeesManagementTestDataGenerator;
import com.example.webapp.test_data.employee.EMPLOYEE;
import com.example.webapp.test_data.login.User;

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
			
		UserDetails user=new User();
		UserDetails actual=service.loadUserByUsername(Integer.toString(1001));
		assertThat(actual).isEqualTo(user);
		assertThat(actual.getAuthorities()).isEqualTo(user.getAuthorities());
	}
	
	@Test
	void test_loadUserByUsername_USER() {
		Employee dummy=EmployeesManagementTestDataGenerator.getEmployeeIdAndName(EMPLOYEE.hoge);
		dummy.setPassword("password");
		dummy.setAuthority(Role.USER);
		doReturn(dummy).when(mapper).selectById(dummy.getEmployeeId());
		
		UserDetails user=new User();
		UserDetails actual=service.loadUserByUsername(Integer.toString(1001));
		assertThat(actual.getAuthorities()).isEqualTo(user.getAuthorities());
	}
}
