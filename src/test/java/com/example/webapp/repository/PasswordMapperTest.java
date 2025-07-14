package com.example.webapp.repository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

@MybatisTest
@Sql("EmployeesManagementMapperTest.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PasswordMapperTest {

	@Autowired
	private PasswordMapper mapper;
	@Autowired
	JdbcTemplate jdbcTemplate;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	void test_updateByEmployeeId() throws SQLException {
		mapper.updateByEmployeeId(1001, encoder.encode("pogepoge"));
		String confirm=jdbcTemplate.queryForObject("SELECT password FROM employees WHERE id=?;",String.class, 1001);
		assertThat(encoder.matches("pogepoge", confirm)).isTrue();
	}
	
//	異常系。BCryptPasswoedEmcoder.encode()を使っている限りは61文字以上になることはない。
//	（公式ドキュメントには生成されるパスワードの桁数に関しての説明はなかったが、
//	経験則と、この記事（https://qiita.com/ponkotuy/items/1a703b866ddf5c9fe80c）より、60桁であると言い切っていいと思う。）
	@Test	
	void test_updateByEmployeeId_invalid_because_password_is_too_long() throws SQLException {
		//65文字
		assertThrows(DataIntegrityViolationException.class
				,()-> mapper.updateByEmployeeId(1001
						, "yMaRKLY7lAMv4lfOBionJTt5TpJw5qKV2SBINwauHFnewTlgcVjtCD57YWh3Vo5Yo"));
	}
}
