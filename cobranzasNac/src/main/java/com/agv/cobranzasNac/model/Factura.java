package com.agv.cobranzasNac.model;

import java.time.LocalDate;
import jakarta.persistence.*; 

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "numero_factura", length = 50, nullable = false, unique = true)
    private String numeroFactura;
    
    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;
    
     //Fecha de vencimiento de la factura.
     //Es calculada por el servicio basándose en la condición de pago del Pedido.
    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;
    // --- FIN DEL CAMPO AÑADIDO ---

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "estado", length = 50)
    private String estado; // Ej: "Pendiente", "Pagada", "En Letras", "Vencida"

    @Column(name = "activo")
    private boolean activo = true; // Por defecto, todo registro nace "activo"
    // --- Relaciones ---

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false, unique = true)
    private Pedido pedido;

    @OneToOne(mappedBy = "factura", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PlanillaLetra planillaLetra;
    
    // --- Constructores, Getters y Setters ---

    public Factura() {
        // Constructor vacío
    }

	public Factura(Long idFactura, String numeroFactura, LocalDate fechaEmision, LocalDate fechaVencimiento,
			Double montoTotal, String estado, boolean activo, Pedido pedido, PlanillaLetra planillaLetra) {
		super();
		this.idFactura = idFactura;
		this.numeroFactura = numeroFactura;
		this.fechaEmision = fechaEmision;
		this.fechaVencimiento = fechaVencimiento;
		this.montoTotal = montoTotal;
		this.estado = estado;
		this.activo = activo;
		this.pedido = pedido;
		this.planillaLetra = planillaLetra;
	}

	public Long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public String getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public LocalDate getFechaEmision() {
		return fechaEmision;
	}

	public void setFechaEmision(LocalDate fechaEmision) {
		this.fechaEmision = fechaEmision;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Double getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
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

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public PlanillaLetra getPlanillaLetra() {
		return planillaLetra;
	}

	public void setPlanillaLetra(PlanillaLetra planillaLetra) {
		this.planillaLetra = planillaLetra;
	}

	@Override
	public String toString() {
		return "Factura [idFactura=" + idFactura + ", numeroFactura=" + numeroFactura + ", fechaEmision=" + fechaEmision
				+ ", fechaVencimiento=" + fechaVencimiento + ", montoTotal=" + montoTotal + ", estado=" + estado
				+ ", activo=" + activo + ", pedido=" + pedido + ", planillaLetra=" + planillaLetra + "]";
	}

    
    
    
}