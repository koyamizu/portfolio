package com.example.webapp.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = IndexController.class, excludeAutoConfiguration = {
	    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
	    org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
	})
public class IndexControllerTest {

		@Autowired
		MockMvc mockMvc;
		
		@Test
		void testShowIndex() throws Exception{
			
			mockMvc.perform(
					get("/")
			)
			.andExpect(status().isOk())
			.andExpect(view().name("index"))
			.andExpect(content().string(containsString("タイムレコーダー")))
			;
		}
}
