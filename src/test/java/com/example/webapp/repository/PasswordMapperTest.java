package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PasswordMapperTest {

	@Autowired
	private PasswordMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	void test_updateByEmployeeId() throws SQLException {
		jdbcTemplate.update("INSERT INTO `employees` VALUES"
			+ "  (1001, '$2a$10$1bByysmSqkXL7c1fEeRgwemvFa0jJZfn3txG5foVXZUbr5cRYdyAS'"
			+ ", '吉塚',   '1986-05-27', '030-1920-8394', '福岡県福岡市博多区吉塚本町13-28'"
			+ ",'ADMIN', '2025-05-07 14:33:32', '2025-05-07 14:33:32', '2025-05-07 14:33:32');");
		mapper.updateByEmployeeId(1001, encoder.encode("hakata00"));
		String confirm=jdbcTemplate.queryForObject("SELECT password FROM employees WHERE id=?;",String.class, 1001);
		assertThat(encoder.matches("hakata00", confirm)).isTrue();
	}
}
