package com.agv.cobranzasNac.model;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*; 

/**
 * Entidad que agrupa un conjunto de Letras asociadas a una única Factura.
 * Representa la "carpeta" o planilla que se gestiona con el cliente o el banco.
 */
@Entity
@Table(name = "planillas_letra")
public class PlanillaLetra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_planilla")
    private Long idPlanilla;
    
    @Column(name = "numero_planilla", length = 50, nullable = true, unique = true)
    private String numeroPlanilla; // Identificador único de la planilla

    /**
     * Define el tipo de gestión para esta planilla.
     * Ej: "EN CARTERA", "EN COBRANZA", "EN DESCUENTO"
     */
    @Column(name = "tipo_gestion", length = 50)
    private String tipoGestion;

    @Column(name = "fecha_envio_banco")
    private LocalDate fechaEnvioBanco; // Fecha en que se envía al banco (si aplica)

    
    @Column(name = "activo")
    private boolean activo = true; // Por defecto, todo registro nace "activo"
    // --- Relaciones ---

    /**
     * Relación Uno a Uno con Factura.
     * Esta entidad (PlanillaLetra) es la "dueña" de la relación.
     * @JoinColumn especifica la clave foránea en esta tabla.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_factura", nullable = false, unique = true)
    private Factura factura;

    /**
     * Relación Uno a Muchos con Letra.
     * Una planilla (1) puede contener muchas letras (N).
     * 'mappedBy = "planillaLetra"' indica que la entidad Letra
     * será la dueña de esta relación (tendrá el @JoinColumn 'id_planilla').
     * 'cascade = CascadeType.ALL' significa que si borramos esta planilla,
     * también se borrarán todas las letras asociadas.
     * 'orphanRemoval = true' asegura que si una letra se quita de esta lista,
     * también se borra de la BD.
     */
    @OneToMany(mappedBy = "planillaLetra", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Letra> letras;
    
    // --- Constructores, Getters y Setters ---

    public PlanillaLetra() {
        // Constructor vacío
    }

	public PlanillaLetra(Long idPlanilla, String numeroPlanilla, String tipoGestion, LocalDate fechaEnvioBanco,
			boolean activo, Factura factura, List<Letra> letras) {
		super();
		this.idPlanilla = idPlanilla;
		this.numeroPlanilla = numeroPlanilla;
		this.tipoGestion = tipoGestion;
		this.fechaEnvioBanco = fechaEnvioBanco;
		this.activo = activo;
		this.factura = factura;
		this.letras = letras;
	}

	public Long getIdPlanilla() {
		return idPlanilla;
	}

	public void setIdPlanilla(Long idPlanilla) {
		this.idPlanilla = idPlanilla;
	}

	public String getNumeroPlanilla() {
		return numeroPlanilla;
	}

	public void setNumeroPlanilla(String numeroPlanilla) {
		this.numeroPlanilla = numeroPlanilla;
	}

	public String getTipoGestion() {
		return tipoGestion;
	}

	public void setTipoGestion(String tipoGestion) {
		this.tipoGestion = tipoGestion;
	}

	public LocalDate getFechaEnvioBanco() {
		return fechaEnvioBanco;
	}

	public void setFechaEnvioBanco(LocalDate fechaEnvioBanco) {
		this.fechaEnvioBanco = fechaEnvioBanco;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public List<Letra> getLetras() {
		return letras;
	}

	public void setLetras(List<Letra> letras) {
		this.letras = letras;
	}

	@Override
	public String toString() {
		return "PlanillaLetra [idPlanilla=" + idPlanilla + ", numeroPlanilla=" + numeroPlanilla + ", tipoGestion="
				+ tipoGestion + ", fechaEnvioBanco=" + fechaEnvioBanco + ", activo=" + activo + ", factura=" + factura
				+ ", letras=" + letras + "]";
	}

    
}
