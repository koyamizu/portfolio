package com.example.webapp.controller_integration;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.example.webapp.common.EmployeeTestDataOld;
import com.example.webapp.entity.Employee;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.helper.EmployeeHelper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase(replace=AutoConfigureTestDatabase.Replace.NONE)
@Sql("../repository/EmployeesManagementMapperTest.sql")
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class EmployeesManagementControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	private EmployeeTestDataOld data;

	@BeforeEach
	void setup() {
		data = new EmployeeTestDataOld();
	}

	@Test
	public void test_showEmployeesList() throws Exception {

		mockMvc.perform(
				get("/employees"))
				.andExpect(content().string(containsString("吉塚")))
				.andExpect(content().string(containsString("古賀")))
				.andExpect(content().string(containsString("黒崎")))
				.andExpect(content().string(containsString("小森江")))
				.andExpect(content().string(containsString("東郷")))
				.andExpect(content().string(containsString("箱崎")))
		//		.andDo(print())
		;
	}

	@Test
	public void test_registerNewEmployee() throws Exception {

		mockMvc.perform(
				get("/employees/register"))
				.andExpect(content().string(containsString("新規従業員登録")))
		//		.andDo(print())
		;
	}

	@Test
	void test_editEmployee() throws Exception {

		mockMvc.perform(
				get("/employees/edit/{employee-id}", 1001))
				.andExpect(content().string(containsString("従業員情報編集")))
		//		.andDo(print())
		;
	}

	@Test
	void test_updateEmployee() throws Exception {

		Employee yoshizuka = data.getYoshizuka();
		EmployeeForm form = EmployeeHelper.convertEmployeeForm(yoshizuka);

		mockMvc.perform(
				post("/employees/update")
						.flashAttr("employeeForm", form)
						.with(csrf()))
				.andExpect(flash().attribute("message", "従業員情報が更新されました"))
		//				.andDo(print())
		;
	}

	@Test
	void test_updateEmployee_NotNullInvalid() throws Exception {

		mockMvc.perform(
				post("/employees/update")
						.with(csrf()))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "employeeId", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "name", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "birth", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "tel", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "address", "NotNull"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "authority", "NotNull"))
		//				.andDo(print())
		;
	}

	@Test
	void test_updateEmployee_PatternInvalid() throws Exception {

		EmployeeForm form = new EmployeeForm();
		form.setBirth("00000000000");
		form.setTel("0000000000");
		mockMvc.perform(
				post("/employees/update")
						.flashAttr("employeeForm", form)
						.with(csrf()))
				.andExpect(view().name("employees/form"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "birth", "Pattern"))
				.andExpect(model().attributeHasFieldErrorCode("employeeForm", "tel", "Pattern"))
//				.andDo(print())
				;
	}

	@Test
	void test_deleteEmployee() throws Exception {

		mockMvc.perform(
				get("/employees/delete/{employee-id}", 1001))
								.andExpect(flash().attribute("message", "従業員ID:1001 吉塚 さんの従業員情報が削除されました"))
//				.andDo(print())
				;
	}
	
	@Test
	void test_deleteEmployee_Error() throws Exception {
		mockMvc.perform(
				get("/employees/delete/{employee-id}", 1007))
				.andExpect(flash().attribute("errorMessage", "そのIDをもつ従業員データは存在しません"));
	}

	@Test
	void test_showEmployeeDetail() throws Exception {

		mockMvc.perform(
				get("/employees/detail/{employee-id}", 1001))
				.andExpect(content().string(containsString("吉塚")))
				.andDo(print())
				;
	}
}
