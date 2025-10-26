package com.mitienda.mt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mitienda.mt.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
	
	// JpaRepository proporciona métodos CRUD básicos para la entidad Producto
	// No es necesario implementar nada aquí, ya que JpaRepository lo hace automáticamente
	// Puedes agregar consultas personalizadas si es necesario
}
