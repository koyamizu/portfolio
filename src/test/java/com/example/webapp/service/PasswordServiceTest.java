package com.example.webapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.webapp.exception.IllegalDataManipulatingException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.repository.PasswordMapper;
import com.example.webapp.service.impl.PasswordServiceImpl;

@ExtendWith(MockitoExtension.class)

public class PasswordServiceTest {
	
	@InjectMocks
	PasswordServiceImpl service;

	@Mock
	PasswordMapper mapper;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	void test_updatePassword() throws IllegalDataManipulatingException, TooLongDataException {
		String newBCryptPassword=encoder.encode("password");
		doNothing().when(mapper).updateByEmployeeId(1001,newBCryptPassword);
		service.updatePassword(1001, newBCryptPassword);
		verify(mapper).updateByEmployeeId(any(),any());
	}
	
	@Test
	void test_updatePassword_invalid_because_password_is_not_60_digit() {
		assertThrows(IllegalDataManipulatingException.class
				,()->service.updatePassword(1001
				, "cAxlmYHqIehciIvklAh1vHNtyIUUWbduBKbWLxQPnCAb04DfVG0jMRsIUK7"));
		assertThrows(IllegalDataManipulatingException.class
				,()->service.updatePassword(1001
						, "cAxlmYHqIehciIvklAh1vHNtyIUUWbduBKbWLxQPnCAb04DfVG0jMRsIUK731"));
	}
	
//	TooLongDateExceptionを放出させているが、BCryptPasswordEncoder.encode()を使ってる限りは60文字以外になることはない。
//	そもそも、if(newBCryptPassword.length()!=60)という条件分岐が先にあるので、60文字以外ならエラーが出るようになっている。
//	一応、それを突破されてしまった（例えば条件分岐後に値が書き換えられるなど）時用にテストメソッドを記述した。
//	本当は、mapper.updateByEmployeeId()に61文字以上のパスワードを、service.updatePasswordには60文字の正当なパスワードをそれぞれ渡してシミュレートをしてみたかった
//	のだが、マッパーメソッドとサービスメソッドに違う値を渡すとエラーになるので、マッパーメソッドにも正当なパスワードを渡し、本来なら放出されるはずのないDataIntegrityViolationExceptionを発生させた。
	@Test
	void test_updatePassword_invalid_because_password_is_too_long() {
		String newBCryptPassword=encoder.encode("password");
		doThrow(DataIntegrityViolationException.class).when(mapper).updateByEmployeeId(1001,newBCryptPassword);
		assertThrows(TooLongDataException.class
				,()->service.updatePassword(1001
				, newBCryptPassword));
		
	}
}
