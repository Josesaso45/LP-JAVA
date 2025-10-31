package com.agv.cobranzasNac.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class Pedido {
	// Implementation goes here
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_pedido")
	private Long idPedido;
	
	@Column(name = "fecha_creacion", nullable = false)
	private LocalDate fechaCreacion;
	
	@Column(name = "total", nullable = false)
	private Double total;
	
	@Column(name = "estado", length = 50)
	private String estado;// Ej: "Rechazado", "Aprobado", "Facturado"
	
	@Column(name = "activo")
	private boolean activo = true; // Por defecto, todo registro nace "activo"
	
	//campos clave para logica de negocio
	/**
     * Define el tipo de pago acordado.
     * Ej: "CONTADO", "CREDITO", "LETRAS"
     */
	@Column(name = "condicion_pago_tipo", length = 20, nullable = false)
	private String CondicionPagoTipo;
	
	/**
     * Días totales del término de pago (ej: 30, 60, 90).
     */
	@Column(name = "termino_dias")
	private Integer terminoDias;
	
	//Número de cuotas o letras a generar (ej: 1, 2, 3).
	@Column(name = "numero_cuotas")
	private Integer numeroCuotas;
	
	// --- Relaciones con otras entidades ---
	
	// Relación con Vendedor (Muchos pedidos a un vendedor)
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	// Relación con Vendedor (Muchos pedidos a un vendedor)
    // Basado en el modelo conceptual 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vendedor", nullable = false)
	private Vendedor vendedor;
	
    /**
     * Relación con la Factura que este pedido genera.
     * 'mappedBy = "pedido"' indica que la entidad Factura
     * es la "dueña" de esta relación (ahí estará el @JoinColumn).
     */
    @OneToOne(mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Factura factura;
	
    
    public Pedido() {
    }

    
    

	// Aquí irían todos los Getters y Setters para cada campo...
    // (Por brevedad no los incluyo todos, pero son necesarios)

	public Pedido(Long idPedido, LocalDate fechaCreacion, Double total, String estado, boolean activo,
			String condicionPagoTipo, Integer terminoDias, Integer numeroCuotas, Cliente cliente, Vendedor vendedor,
			Factura factura) {
		super();
		this.idPedido = idPedido;
		this.fechaCreacion = fechaCreacion;
		this.total = total;
		this.estado = estado;
		this.activo = activo;
		CondicionPagoTipo = condicionPagoTipo;
		this.terminoDias = terminoDias;
		this.numeroCuotas = numeroCuotas;
		this.cliente = cliente;
		this.vendedor = vendedor;
		this.factura = factura;
	}




	public Long getIdPedido() {
		return idPedido;
	}



	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}



	public LocalDate getFechaCreacion() {
		return fechaCreacion;
	}



	public void setFechaCreacion(LocalDate fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}



	public Double getTotal() {
		return total;
	}



	public void setTotal(Double total) {
		this.total = total;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isActivo() {
	    return activo;
	}

	public void setActivo(boolean activo) {
	    this.activo = activo;
	}

	public String getCondicionPagoTipo() {
		return CondicionPagoTipo;
	}



	public void setCondicionPagoTipo(String condicionPagoTipo) {
		CondicionPagoTipo = condicionPagoTipo;
	}



	public Integer getTerminoDias() {
		return terminoDias;
	}



	public void setTerminoDias(Integer terminoDias) {
		this.terminoDias = terminoDias;
	}



	public Integer getNumeroCuotas() {
		return numeroCuotas;
	}



	public void setNumeroCuotas(Integer numeroCuotas) {
		this.numeroCuotas = numeroCuotas;
	}



	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public Vendedor getVendedor() {
		return vendedor;
	}



	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}



	public Factura getFactura() {
		return factura;
	}



	public void setFactura(Factura factura) {
		this.factura = factura;
	}




	@Override
	public String toString() {
		return "Pedido [idPedido=" + idPedido + ", fechaCreacion=" + fechaCreacion + ", total=" + total + ", estado="
				+ estado + ", activo=" + activo + ", CondicionPagoTipo=" + CondicionPagoTipo + ", terminoDias="
				+ terminoDias + ", numeroCuotas=" + numeroCuotas + ", cliente=" + cliente + ", vendedor=" + vendedor
				+ ", factura=" + factura + "]";
	}
	
	
}
