package com.example.demo2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo2.model.Producto;

@Controller
public class ProoductoContoller {
	
	@GetMapping("/")
	public String index() {
		return "index.html";
	}
	
	@GetMapping("/producto")
	public String mostrarProducto(Model model) {
		
		/*Producto objPro = new Producto();
		
		objPro.setId(1L);
		objPro.setNombre("Laptop");
		objPro.setPrecio(1500.00);
		objPro.setStock(10);modo 01*/
		
		List<String> listProd =  new ArrayList<String>;
		
		model.addAttribute("producto", objPro);
		
		
		return "producto";
	}
}
