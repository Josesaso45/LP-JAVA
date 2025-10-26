package com.mitienda.mt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoriaController {
	
	@GetMapping("/categoria")
	public String listarCategoria() {
		
		
		
		return "categoria.html";
	}
}
