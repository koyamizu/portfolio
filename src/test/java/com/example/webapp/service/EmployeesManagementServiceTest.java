package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.common.EmployeeTestData;
import com.example.webapp.entity.Employee;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.impl.EmployeesManagementServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeesManagementServiceTest {

	@InjectMocks
	EmployeesManagementServiceImpl service;
	
	@Mock
	EmployeesManagementMapper employeesManagementMapper;

	private EmployeeTestData data=new EmployeeTestData();
	
	@Test
	void testSelectEmployeeById() {
		Employee yoshizuka=data.getYoshizuka();
		
		doReturn(yoshizuka).when(employeesManagementMapper).selectById(1001);
		
		Employee actual=service.selectEmployeeById(1001);
		
		assertThat(actual).isEqualTo(yoshizuka);
	}
	
	@Test
	void testSelectAllEmployees() {
		List<Employee> employees=data.createAllEmployees();
		
		doReturn(employees).when(employeesManagementMapper).selectAll();
		List<Employee> actuals=service.selectAllEmployees();
		
		assertThat(actuals).isEqualTo(employees);
	}
	
	@Test
	void testSelectAllIdAndName() {
		List<Employee> employees=data.createTestEmployeeIdAndName();
		
		doReturn(employees).when(employeesManagementMapper).selectAllIdAndName();
		
		List<Employee> actuals=service.selectAllIdAndName();
		
		assertThat(actuals).isEqualTo(employees);
	}

	@Test
	void testSelectEmployeeIdByName() {
		Integer id=1001;
		doReturn(id).when(employeesManagementMapper).selectIdByName("吉塚");
		Integer actual=service.selectEmployeeIdByName("吉塚");
		assertThat(actual).isEqualTo(id);
	}
	
	@Test
	void testInsertEmployee() {
		Employee newEmployee=data.getChihaya();
		doNothing().when(employeesManagementMapper).insert(newEmployee);
		service.insertEmployee(newEmployee);
		verify(employeesManagementMapper).insert(any());
	}
	
	@Test
	void testUpdateEmployee() {
		Employee employee=new Employee();
		employee.setName("古賀");
		doReturn(employee).when(employeesManagementMapper).selectById(1002);
		Employee koga=service.selectEmployeeById(1002);

		log.info("名前変更前："+koga.getName());
		koga.setName("鹿部");
		
		doNothing().when(employeesManagementMapper).update(koga);
		service.updateEmployee(koga);
		
		verify(employeesManagementMapper).update(koga);
	}
	
	@Test
	void testDeleteEmployeeById() {
		Employee yoshizuka=data.getYoshizuka();
		
		doReturn(yoshizuka).when(employeesManagementMapper).selectById(1001);
		doNothing().when(employeesManagementMapper).deleteById(1001);
		
		assertThat(service.selectEmployeeById(1001)).isNotNull();
		service.deleteEmployeeById(1001);
		assertThat(service.selectEmployeeById(1001)).isNull();
	}
}
