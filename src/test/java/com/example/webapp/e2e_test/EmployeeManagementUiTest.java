package com.example.webapp.e2e_test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;

import com.codeborne.selenide.Configuration;

@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
@Sql("../repository/EmployeesManagementMapperTest.sql")
@WithMockUser(username = "admin", authorities = { "ADMIN", "USER" })
public class EmployeeManagementUiTest {

	@Value("${local.server.port}")
	int randomPort;
	
	@BeforeEach
	void setup() {
		Configuration.browser="chrome";
		Configuration.baseUrl="http://localhost:"+randomPort;
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String dateTime=LocalDateTime.now().format(formatter);
		Configuration.reportsFolder="build/reports/screenshots/EmployeeManagementTest/"+dateTime;
	}
	
	@Test
	void test() {
		open("/employees");
		screenshot("LoginPage");
		$("input[name=employeeIdInput]").setValue("1001");
		$("input[name=passwordInput]").setValue("yoshizuka01");
		$("form").submit();
		assertEquals("従業員管理",title());
		screenshot("EmployeesListPage");
		$$("a[href*='/employees/detail/']").last().click();
		screenshot("DetailHakozaki");
		$$("table tr td").get(1).should(exactText("箱崎"));
	}
}
