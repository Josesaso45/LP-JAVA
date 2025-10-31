package com.agv.cobranzasNac.model;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre; 

    @Column(name = "tipo_cliente", length = 50)
    private String tipoCliente; //  (Ej: "Minorista", "Mayorista", "Distribuidor")

    @Column(name = "linea_comercial", length = 100)
    private String lineaComercial; //  (Ej: "Petmedica", "Interpet", "Agrovet")

    @Column(name = "limite_credito", precision = 10, scale = 2)
    private BigDecimal limiteCredito; //  (Usamos BigDecimal para dinero)

    @Column(name = "estado", length = 50)
    private String estado; //  (Ej: "Activo", "Moroso", "Bloqueado")

    // --- Campo para Baja Lógica ---
    @Column(name = "activo")
    private boolean activo = true;

    // --- Relación Inversa ---
    // Un Cliente (1) puede tener muchos Pedidos (N)
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
    
    
    // --- Constructores, Getters y Setters ---

    public Cliente() {
        // Constructor vacío
    }
    
    public Cliente(Long idCliente, String nombre, String tipoCliente, String lineaComercial,
			BigDecimal limiteCredito, String estado, boolean activo, List<Pedido> pedidos) {
		this.idCliente = idCliente;
		this.nombre = nombre;
		this.tipoCliente = tipoCliente;
		this.lineaComercial = lineaComercial;
		this.limiteCredito = limiteCredito;
		this.estado = estado;
		this.activo = activo;
		this.pedidos = pedidos;
	}

    // (Aquí irían todos los Getters y Setters)

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getLineaComercial() {
        return lineaComercial;
    }

    public void setLineaComercial(String lineaComercial) {
        this.lineaComercial = lineaComercial;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
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

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

	@Override
	public String toString() {
		return "Cliente [idCliente=" + idCliente + ", nombre=" + nombre + ", tipoCliente=" + tipoCliente
				+ ", lineaComercial=" + lineaComercial + ", limiteCredito=" + limiteCredito + ", estado=" + estado
				+ ", activo=" + activo + ", pedidos=" + pedidos + "]";
	}
    
    
}