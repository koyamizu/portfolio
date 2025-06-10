package com.example.webapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.webapp.service.EmployeesManagementService;

@WebMvcTest(EmployeesManagementController.class)
public class EmployeesManagementControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockitoBean
	EmployeesManagementService employeesManagementService;
	
	@Test
	void testShowShiftSchedule() throws Exception{
		
	}
}
