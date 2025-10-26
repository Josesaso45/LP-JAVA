package com.mitienda.mt.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @Column(name = "cod_cliente")
    private Long codCliente;

    @Column(name = "nom_cliente")
    private String nomCliente;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "dni")
    private String dni;

    // --- Relación ManyToOne (la llave foránea) ---
    @ManyToOne
    @JoinColumn(name = "id_tipoclie", nullable = false)
    private TipoCliente tipoCliente;

    // --- Getters y Setters ---

    public Long getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(Long codCliente) {
        this.codCliente = codCliente;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(TipoCliente tipoCliente) {
        this.tipoCliente = tipoCliente;
    }
}