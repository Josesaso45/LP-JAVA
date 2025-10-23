package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {
	
	@GetMapping("/hello")
	public String helloworld() {
		return "Hello World!";
	}
	
	@GetMapping("/bye")
	public String byeworld() {
		return "Bye World!";
	}
}
