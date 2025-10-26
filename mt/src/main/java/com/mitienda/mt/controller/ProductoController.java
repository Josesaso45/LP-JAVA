package com.mitienda.mt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mitienda.mt.service.ProductoService;


@Controller
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/productos")
	public String listarProductos(Model model) {
		
		
		model.addAttribute("productos", productoService.obtenerProductos());
		
		return "productos";
	}
}