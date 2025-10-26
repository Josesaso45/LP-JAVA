package com.mitienda.mt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitienda.mt.model.Producto;
import com.mitienda.mt.repository.ProductoRepository;

import jakarta.persistence.Id;

@Service
public class ProductoService {	
	
	@Autowired
	private ProductoRepository productoRepository;
	
	//SERVICIOS BASICOS CRUD
	
	//Listar todos los productos
	public List<Producto> obtenerProductos() {
		return productoRepository.findAll();
	}
	
	//Guardar un producto
	public  Producto guardarProductos(Producto producto) {
		return productoRepository.save(producto);
	}
	
	//Actualizar un producto
	public Producto buscarPorIdProductos(Long id) {
		
		return productoRepository.findById(id).orElse(null);
		// .orElse(null) significa: "si la caja está vacía, devuelve null"
	}
	
	//Eliminar un producto
	public void eliminarPorIdProductos(Long id) {
		productoRepository.deleteById(id);
	}
}
