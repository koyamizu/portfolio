package com.example.webapp.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.webapp.entity.Role;

@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping
	public String showIndex(HttpSession session){
		session.removeAttribute("from");
		return "index";
	}

	@GetMapping("admin")
	public String showAdminMenu(HttpSession session) {
		session.setAttribute("from","admin");
		return "menu/admin";
	}
	
	@GetMapping("user")
	public String showUserMenu(HttpSession session,Authentication auth) {
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		if(isAdmin) {
			return "redirect:/admin";
		}
		session.setAttribute("from", "user");
		return "menu/user";
	}
	
	//デベロッパーツールを開いた状態でログインするとエラーになるので、エラーページを用意した。
	@GetMapping(".well-known/appspecific/com.chrome.devtools.json")
	public String showOpenDevtoolError() {
		return "error/devtool";
	}
}
