package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping
	public String showIndex(){
		return "index";
	}
	
	@GetMapping("/admin")
	public String showAdminMenu() {
		return "menu/admin";
	}
	
	@GetMapping("/user")
	public String showUserMenu() {
		return "menu/user";
	}
}
