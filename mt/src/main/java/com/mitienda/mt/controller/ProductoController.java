package com.mitienda.mt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mitienda.mt.model.Producto;
import com.mitienda.mt.service.ProductoService;


@Controller
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	private ProductoService productoService;
	
	@GetMapping("/listar")
	public String listarProductos(Model model) {
		
		model.addAttribute("productos", productoService.obtenerProductos());
		
		model.addAttribute("ProductoAInsertar",new Producto());
		
		return "productos";
	}
	
	@PostMapping("/guardar")
	public String guardarProducto(@ModelAttribute("ProductoAInsertar") Producto producto, RedirectAttributes redirectAttributes) {
		productoService.guardarProductos(producto);
		
		redirectAttributes.addFlashAttribute("guardadoExitoso", "Producto guardado exitosamente");
		
		return "redirect:/productos/listar";
	}
	
	@GetMapping("/actualizar/{id}")
	public String actualizarProducto(@PathVariable("id") Long id, Model model) {
		
		Producto productoExistente = productoService.buscarPorIdProductos(id);
		
		model.addAttribute("ProductoAInsertar", productoExistente);
		
		model.addAttribute("productos", productoService.obtenerProductos());
		
		return "redirect:/productos/listar";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminarProducto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		
		productoService.eliminarPorIdProductos(id);
		
		redirectAttributes.addFlashAttribute("eliminadoExitoso", "Producto eliminado exitosamente");
		
		return "redirect:/productos/listar";
	}
	
}