package com.example.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(
						authz -> authz.requestMatchers("/css/**", "/js/**","/login","/","/shift","/api/event/all").permitAll()
								.requestMatchers("/employees/**","/time_recorder","/shift/management").hasAuthority("ADMIN")
								.anyRequest().authenticated())
				.formLogin(form -> form.loginPage("/login")
						.loginProcessingUrl("/authentication")
						.usernameParameter("employeeIdInput")
						.passwordParameter("passwordInput")
						.defaultSuccessUrl("/")
						.failureUrl("/login?error"))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/login?logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID"));
		return http.build();
	}
}
