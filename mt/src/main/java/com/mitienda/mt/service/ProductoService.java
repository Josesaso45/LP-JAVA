package com.mitienda.mt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mitienda.mt.model.Producto;
import com.mitienda.mt.repository.ProductoRepository;

@Service
public class ProductoService {	
	
	@Autowired
	private ProductoRepository productoRepository;
	
	//SERVICIOS BASICOS CRUD
	
	//Listar todos los productos
	public List<Producto> obtenerProductos() {
		return productoRepository.findAll();
	}
	
	//Obtener un producto por ID
	
	
	//Eliminar un producto por ID
	
	//Actualizar un producto
	
}
