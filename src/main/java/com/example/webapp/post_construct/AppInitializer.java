package com.example.webapp.post_construct;

import jakarta.annotation.PostConstruct;

import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.stereotype.Component;

import com.example.webapp.utility.DateTimeIntervalGenerator;

@Component
public class AppInitializer {

//	午前0時までセッション情報を保持する。当日欠勤者の情報が30分（springのデフォルトの時間）で消えてしまうのを防ぐため。
	@PostConstruct
	public ServletContextInitializer initializer() {
		int byTheEndOfToday=DateTimeIntervalGenerator.getIntervalBetweenMidNight();
	    return servletContext -> {
	        servletContext.getSessionCookieConfig().setMaxAge(byTheEndOfToday);
	    };
	}
}
