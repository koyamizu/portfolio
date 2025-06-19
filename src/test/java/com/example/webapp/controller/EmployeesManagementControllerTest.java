package com.example.webapp.controller;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.webapp.common.EmployeeTestData;
import com.example.webapp.entity.Employee;
import com.example.webapp.entity.Role;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;
import com.example.webapp.service.EmployeesManagementService;

import lombok.extern.slf4j.Slf4j;

//@WebMvcTest(controllers = EmployeesManagementController.class, excludeAutoConfiguration = {
//	    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
//	    org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
//	})
//@Import(SecurityConfig.class)
@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeesManagementController.class)
@Slf4j
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class EmployeesManagementControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockitoBean
	EmployeesManagementService service;

	private EmployeeTestData data;

	@BeforeEach
	void setup() {
		data = new EmployeeTestData();
	}

	@Test
	void test_showEmployeesList() throws Exception {

		List<Employee> employees = data.createAllEmployees();

		doReturn(employees).when(service).getAllEmployees();

		List<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(Role.USER.toString()));

		mockMvc.perform(
				get("/employees"))
				.andExpect(status().isOk())
				.andExpect(view().name("employees/list"))
				.andExpect(content().string(containsString("吉塚")))
		//		.andDo(print())
		;
	}

	@Test
	void test_registerNewEmployee() throws Exception {

		MvcResult result = mockMvc.perform(
				get("/employees/register"))
				.andExpect(status().isOk())
				.andExpect(view().name("employees/form"))
				//		.andDo(print())
				.andReturn();

		EmployeeForm resultForm = (EmployeeForm) result.getModelAndView().getModel().get("employeeForm");
		assertThat(resultForm.getIsNew()).isEqualTo(true);
	}

	@Test
	void test_editEmployee() throws Exception {

		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(service).getEmployeeForm(1001);
		mockMvc.perform(
				get("/employees/edit/{employee-id}", 1001))
				.andExpect(status().isOk())
				.andExpect(view().name("employees/form"))
		//		.andDo(print())
		;
	}

	@Test
	void test_editEmployee_Error() throws Exception {
		mockMvc.perform(
				get("/employees/edit/{employee-id}", 1007))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("/employees"))
				.andExpect(flash().attribute("errorMessage", "そのIDをもつ従業員データは存在しません"));
	}

	@Test
	void test_updateEmployee() throws Exception {

		Employee yoshizuka = data.getYoshizuka();
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(yoshizuka);

		mockMvc.perform(
				post("/employees/update")
						.flashAttr("employeeForm", form)
						.with(csrf()))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/employees"))
				.andExpect(flash().attribute("message", "従業員情報が更新されました"))
		//		.andDo(print())
		;
	}

	@Test
	void test_updateEmployee_NotNullInvalid() throws Exception {

		mockMvc.perform(
				post("/employees/update")
						.with(csrf()))
				.andExpect(view().name("employees/form"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "name", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "birth", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "tel", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "address", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "authority", "NotNull"))
		//		.andDo(print())
		;
	}

	@Test
	void test_updateEmployee_PatternInvalid() throws Exception {

		EmployeeForm form = new EmployeeForm();
		form.setName("0123");
		form.setBirth("00000000000");
		form.setTel("0000000000");
		form.setAddress("千早4-9-9");
		mockMvc.perform(
				post("/employees/update")
						.flashAttr("employeeForm", form)
						.with(csrf()))
				.andExpect(view().name("employees/form"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "name", "Pattern"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "birth", "Pattern"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "tel", "Pattern"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "address", "Pattern"))
				.andDo(print())
		;
	}

	@Test
	void test_deleteEmployee() throws Exception {

		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(service).getEmployeeForm(1001);
		doNothing().when(service).deleteEmployee(1001);

		mockMvc.perform(
				get("/employees/delete/{employee-id}", 1001))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/employees"))
				.andExpect(flash().attribute("message", "従業員ID:" + yoshizuka.getEmployeeId() + " " + yoshizuka.getName()
						+ " さんの従業員情報が削除されました"));
	}

	//serviceメソッドに例外放出を設定していないのでいったん保留
	//	@Test
	//	void testThrowsExceptionFromDeleteEmployee() throws Exception{
	//		
	//		Employee yoshizuka=data.getYoshizuka();
	//		doReturn(yoshizuka).when(service).selectEmployeeById(1001);
	//		doThrow(new Exception()).when(service).deleteEmployeeById(1001);
	//		
	//		assertThatThrownBy(()->
	//		mockMvc.perform(
	//				get("/employees/delete/{employee-id}",1001)
	//				))
	//		.hasCause(new Exception())
	//		;
	//	}

	@Test
	void test_deleteEmployee_Error() throws Exception {

		doReturn(null).when(service).getEmployeeForm(1001);

		mockMvc.perform(
				get("/employees/delete/{employee-id}", 1001))
				.andExpect(status().is3xxRedirection())
				.andExpect(redirectedUrl("/employees"))
				.andExpect(flash().attribute("errorMessage", "そのIDをもつ従業員データは存在しません"));
	}

	@Test
	void test_showEmployeeDetail() throws Exception {

		Employee yoshizuka = data.getYoshizuka();
		doReturn(yoshizuka).when(service).getEmployeeForm(1001);

		mockMvc.perform(
				get("/employees/detail/{employee-id}", 1001))
				.andExpect(status().isOk())
				.andExpect(view().name("employees/detail"))
				.andExpect(model().attribute("employee", yoshizuka))
				.andDo(print());
	}
}
