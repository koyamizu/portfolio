package com.example.webapp.e2e_test;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class EmployeeManagementUiTest {

	@Value("${local.server.port}")
	int randomPort;
	
	@BeforeEach
	void setup() {
		Configuration.baseUrl="http://localhost:"+randomPort;
	}
}
