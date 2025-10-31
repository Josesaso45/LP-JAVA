package com.agv.cobranzasNac.model;

import java.time.LocalDate;
import java.math.BigDecimal; // Usar BigDecimal es una mejor práctica para dinero
import jakarta.persistence.*; 

/**
 * Entidad que representa una Letra (cuota) individual.
 * Esta es la unidad principal para el seguimiento de cobranzas.
 */
@Entity
@Table(name = "letras")
public class Letra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_letra")
    private Long idLetra;
    
    @Column(name = "numero_letra", length = 50, unique = true,nullable = true)
    private String numeroLetra;

    /**
     * Almacena el número de cuota, ej: "1/3", "2/3", "3/3".
     * Útil para la reportería.
     */
    @Column(name = "numero_cuota", length = 10)
    private String numeroCuota;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto; // Usamos BigDecimal para precisión financiera

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento; // La fecha clave para el reporte

    @Column(name = "estado", length = 50, nullable = false)
    private String estado; // Ej: "Generada", "Aceptada", "Pagada", "Protestada"

    @Column(name = "activo")
    private boolean activo = true;
    // --- Relaciones ---

    /**
     * Relación Muchos a Uno con PlanillaLetra.
     * Muchas letras (N) pertenecen a una planilla (1).
     * Esta entidad (Letra) es la "dueña" de la relación.
     * @JoinColumn especifica la clave foránea en esta tabla.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_planilla", nullable = false)
    private PlanillaLetra planillaLetra;
    
    // --- Constructores, Getters y Setters ---

    public Letra() {
        // Constructor vacío
    }

	public Letra(Long idLetra, String numeroLetra, String numeroCuota, BigDecimal monto, LocalDate fechaVencimiento,
			String estado, boolean activo, PlanillaLetra planillaLetra) {
		super();
		this.idLetra = idLetra;
		this.numeroLetra = numeroLetra;
		this.numeroCuota = numeroCuota;
		this.monto = monto;
		this.fechaVencimiento = fechaVencimiento;
		this.estado = estado;
		this.activo = activo;
		this.planillaLetra = planillaLetra;
	}

	public Long getIdLetra() {
		return idLetra;
	}

	public void setIdLetra(Long idLetra) {
		this.idLetra = idLetra;
	}

	public String getNumeroLetra() {
		return numeroLetra;
	}

	public void setNumeroLetra(String numeroLetra) {
		this.numeroLetra = numeroLetra;
	}

	public String getNumeroCuota() {
		return numeroCuota;
	}

	public void setNumeroCuota(String numeroCuota) {
		this.numeroCuota = numeroCuota;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public LocalDate getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(LocalDate fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
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

	public PlanillaLetra getPlanillaLetra() {
		return planillaLetra;
	}

	public void setPlanillaLetra(PlanillaLetra planillaLetra) {
		this.planillaLetra = planillaLetra;
	}

	@Override
	public String toString() {
		return "Letra [idLetra=" + idLetra + ", numeroLetra=" + numeroLetra + ", numeroCuota=" + numeroCuota
				+ ", monto=" + monto + ", fechaVencimiento=" + fechaVencimiento + ", estado=" + estado + ", activo="
				+ activo + ", planillaLetra=" + planillaLetra + "]";
	}
    
    

    
}