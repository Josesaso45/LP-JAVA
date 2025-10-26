package com.mitienda.mt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//Java Bean que representa un producto en la tienda
//Representa los datos en una abstracción sencilla
//POJO: Plain Old Java Object
//Es una clase simple con atributos privados y métodos públicos para acceder a ellos

@Entity
@Table(name = "tbl_productos")
public class Producto {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProducto;
	private String nombreProducto;
	private Double precioProducto;
	private Integer stockProducto;

	public Producto() {
		
	}
	
	public Producto(Long idproducto, String nombreProducto, Double precioProducto, Integer stockProducto) {
		this.idProducto = idproducto;
		this.nombreProducto = nombreProducto;
		this.precioProducto = precioProducto;
		this.stockProducto = stockProducto;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public Double getPrecioProducto() {
		return precioProducto;
	}

	public void setPrecioProducto(Double precioProducto) {
		this.precioProducto = precioProducto;
	}

	public Integer getStockProducto() {
		return stockProducto;
	}

	public void setStockProducto(Integer stockProducto) {
		this.stockProducto = stockProducto;
	}

	@Override
	public String toString() {
		return "Producto [idproducto=" + idProducto + ", nombreProducto=" + nombreProducto + ", precioProducto="
				+ precioProducto + ", StockProducto=" + stockProducto + "]";
	}
	
	
}
