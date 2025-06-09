package com.example.webapp.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.webapp.entity.Employee;
import com.example.webapp.repository.EmployeesManagementMapper;
import com.example.webapp.service.impl.EmployeesManagementServiceImpl;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class EmployeesManagementServiceTest {

	@InjectMocks
	EmployeesManagementServiceImpl employeesManagementService;
	
	@Mock
	EmployeesManagementMapper employeesManagementMapper;
	
	@Test
	void testSelectEmployeeById() {
		Employee employee=new Employee();
		employee.setName("吉塚");
		doReturn(employee).when(employeesManagementMapper).selectById(1001);
		Employee actual=employeesManagementService.selectEmployeeById(1001);
		assertThat(actual.getName()).isEqualTo("吉塚");
	}
	
	@Test
	void testSelectAllEmployees() {
		List<Integer> ids=List.of(1001,1002,1003,1004,1005,1006);
//		配列が作成されていない。
//		java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
		List<Employee> employees=new ArrayList<Employee>(6);
		for(int i=0;i<ids.size();i++) {
			employees.get(i).setEmployeeId(ids.get(i));
		}
		doReturn(employees).when(employeesManagementMapper).selectAll();
		List<Employee> actuals=employeesManagementService.selectAllEmployees();
		assertThat(actuals.size()).isEqualTo(6);
		employees.stream().forEach(e->log.info(e.toString()));
	}
	
	@Test
	void testSelectAllIdAndName() {
		Map<Integer,String> data=Map.of(
				1001,"吉塚"
				,1002,"古賀"
				,1003,"黒崎"
				,1004,"東郷"
				,1005,"小森江"
				,1006,"箱崎");
		//従業員IDのみのリストを作成
		List<Integer> keys=data.keySet().stream().toList();
		
		List<Employee> employees=new ArrayList<Employee>();
		
		//Employee配列のIDと名前にそれぞれ値を格納。
		//Employeeクラスはフィールドが多く、コンストラクタで初期化すると
		//nullだらけになって可視性が悪くなるのでこういう方法をとっている。
		for(int i=0;i<employees.size();i++) {
			employees.get(i).setEmployeeId(keys.get(i));
			employees.get(i).setName(data.get(keys.get(i)));
		}
		
		doReturn(employees).when(employeesManagementMapper).selectAllIdAndName();
		
		List<Employee> actuals=employeesManagementService.selectAllIdAndName();
		for(int i=0;i<actuals.size();i++) {
			Integer key=actuals.get(i).getEmployeeId();
			String value=actuals.get(i).getName();
			assertThat(value).isEqualTo(employees.get(key));
		}
	}

//	
//	List<Employee> selectAllIdAndName();
//	
//	Integer selectEmployeeIdByName(String name);
//	
//	void insertEmployee(Employee employee);
//	
//	void updateEmployee(Employee employee);
//	
//	void deleteEmployeeById(Integer employeeId);
}
