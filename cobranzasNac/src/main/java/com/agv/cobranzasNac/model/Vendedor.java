package com.agv.cobranzasNac.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "vendedores")
public class Vendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vendedor")
    private Long idVendedor;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre; 

    @Column(name = "codigo", nullable = false, unique = true, length = 20)
    private String codigo; 

    @Column(name = "activo")
    private boolean activo = true;

    // Un Vendedor (1) puede tener muchos Pedidos (N)
    @OneToMany(mappedBy = "vendedor")
    private List<Pedido> pedidos;

    
    // --- Constructores, Getters y Setters ---

    public Vendedor() {
        // Constructor vacío
    }
    
    public Vendedor(Long idVendedor, String nombre, String codigo, boolean activo, List<Pedido> pedidos) {
		this.idVendedor = idVendedor;
		this.nombre = nombre;
		this.codigo = codigo;
		this.activo = activo;
		this.pedidos = pedidos;
	}

    // (Getters y Setters para todos los campos...)

    public Long getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(Long idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

	@Override
	public String toString() {
		return "Vendedor [idVendedor=" + idVendedor + ", nombre=" + nombre + ", codigo=" + codigo + ", activo=" + activo
				+ ", pedidos=" + pedidos + "]";
	}
    
    
}