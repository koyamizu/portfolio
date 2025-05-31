package com.example.webapp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.LoginUser;
import com.example.webapp.entity.Role;
import com.example.webapp.repository.EmployeesManagementMapper;

import lombok.RequiredArgsConstructor;

//SecurityConfigのどのタイミングで使われているのか？

@Service
@RequiredArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService {

	private final EmployeesManagementMapper mapper;
	@Override
	public UserDetails loadUserByUsername(String employeeIdStr) throws UsernameNotFoundException {
		Integer employeeId=Integer.parseInt(employeeIdStr);
		Employee employee=mapper.selectById(employeeId);
		if(employee!=null) {
			return new LoginUser(String.valueOf(employee.getEmployeeId()),
					employee.getPassword(),
					getAuthorityList(employee.getAuthority()),
					employee.getName()
					);
		}
		throw new UsernameNotFoundException(
				employeeId + "=>指定しているIDをもつ従業員は存在しません");
	}
	
	private List<GrantedAuthority> getAuthorityList(Role role){
		
		List<GrantedAuthority> authorities=new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority(role.name()));
		
		if(role==Role.ADMIN) {
			authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));
		}
		/*		role=ADMINの場合、authorities= {ADMIN,USER}
				role=USERの場合、authorities= {USER}*/
		return authorities;
	}
}
