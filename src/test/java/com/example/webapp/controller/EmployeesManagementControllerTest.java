package com.example.webapp.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.webapp.common.EmployeeTestData;
import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.service.EmployeesManagementService;

@WebMvcTest(controllers = EmployeesManagementController.class, excludeAutoConfiguration = {
	    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
	    org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
	})
public class EmployeesManagementControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	EmployeesManagementService service;
	
	private EmployeeTestData data=new EmployeeTestData();
	
	@Test
	void testShowEmployeesList() throws Exception{
		
		List<Employee> employees=data.createAllEmployees();
		
		doReturn(employees).when(service).selectAllEmployees();
		
		mockMvc.perform(
				get("/employees")
		)
		.andExpect(status().isOk())
		.andExpect(view().name("employees/list"))
		.andExpect(content().string(containsString("吉塚")))
//		.andDo(print())
		;
	}
	
	@Test
	void testRegisterNewEmployee() throws Exception{
		
		MvcResult result=mockMvc.perform(
				get("/employees/register")
				)
		.andExpect(status().isOk())
		.andExpect(view().name("employees/form"))
//		.andDo(print())
		.andReturn()
		;
		
		EmployeeForm resultForm=(EmployeeForm)result.getModelAndView().getModel().get("employeeForm");
		assertThat(resultForm.getIsNew()).isEqualTo(true);
	}
	
	@Test
	void testEditEmployee() throws Exception{
		
		Employee yoshizuka=data.getYoshizuka();
		doReturn(yoshizuka).when(service).selectEmployeeById(1001);
		mockMvc.perform(
				get("/employees/edit/{employee-id}",1001)
				)
		.andExpect(status().isOk())
		.andExpect(view().name("employees/form"))
//		.andDo(print())
		;
	}
	
	@Test
	void testErrorEditEmployee() throws Exception{
		mockMvc.perform(
				get("/employees/edit/{employee-id}",1007)
				)
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/employees"))
		.andExpect(flash().attribute("errorMessage","そのIDをもつ従業員データは存在しません"))
		;
	}
	
	@Test
	void testUpdateEmployee() throws Exception{

		Employee yoshizuka=data.getYoshizuka();
		EmployeeForm form=EmployeeHelper.convertEmployeeForm(yoshizuka);

		mockMvc.perform(
				post("/employees/update")
				.flashAttr("employeeForm",form)
				)
		.andExpect(status().is3xxRedirection())
		.andExpect(model().hasNoErrors())
		.andExpect(redirectedUrl("/employees"))
		.andExpect(flash().attribute("message","従業員情報が更新されました"))
//		.andDo(print())
		;
	}
	
	@Test
	void testNotNullInvalid() throws Exception{
		
		mockMvc.perform(
				post("/employees/update")
				)
		.andExpect(view().name("employees/form"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","employeeId","NotNull"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","name","NotNull"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","birth","NotNull"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","tel","NotNull"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","address","NotNull"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","authority","NotNull"))
		.andDo(print())
		;
	}
	
	@Test
	void testPatternInvalid() throws Exception{
		
		EmployeeForm form=new EmployeeForm();
		form.setBirth("00000000000");
		form.setTel("0000000000");
		mockMvc.perform(
				post("/employees/update")
				.flashAttr("employeeForm", form)
				)
		.andExpect(view().name("employees/form"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","birth","Pattern"))
		.andExpect(model().attributeHasFieldErrorCode("employeeForm","tel","Pattern"))
//		.andDo(print())
		;
	}
	
	@Test
	void testDeleteEmployee() throws Exception{
		
		Employee yoshizuka=data.getYoshizuka();
		doReturn(yoshizuka).when(service).selectEmployeeById(1001);
		mockMvc.perform(
				get("/delete/{employee-id}",1001)
				)
		//service.deleteEmployeeByIdの記述から。コントローラクラスの77行目部分から
		
	}
}
