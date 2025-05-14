package com.example.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class IndexController {
	@GetMapping
	public String showIndex(HttpSession session){
		session.invalidate();
		return "index";
	}
	
	@GetMapping("to_time_recorder")
	public String showTimeRecorder(HttpSession session) {
		session.setAttribute("from", "time_recorder");
		return "redirect:/time_recorder";
	}

	@GetMapping("admin")
	public String showAdminMenu(HttpSession session) {
		session.setAttribute("from","admin");
		/*		ブラウザバックをしたとき、showIndexのsession.invalidate()によってセッションが破棄されるようにしたが
				ブラウザバック→ブラウザフォワードしたときにログインなしで遷移できるので、ログイン後15分経過したらセッションが破棄されるようにした。*/
		session.setMaxInactiveInterval(1800);
		return "menu/admin";
	}
	
	@GetMapping("user")
	public String showUserMenu(HttpSession session) {
		session.setAttribute("from", "user");
		session.setMaxInactiveInterval(1800);
		return "menu/user";
	}
}
