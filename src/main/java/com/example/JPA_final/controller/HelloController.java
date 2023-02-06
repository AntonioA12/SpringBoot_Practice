package com.example.JPA_final.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

	/*
	 *  웹 서버 최초 실행시 실행되는 컨트롤러 
	 */
	
	@GetMapping("hello")
	public String hello(Model model) {
		model.addAttribute("data", "hello!");
		return "hello";
	}
	
	@GetMapping("/")
	public String home() {
		return "home";
	}
}
