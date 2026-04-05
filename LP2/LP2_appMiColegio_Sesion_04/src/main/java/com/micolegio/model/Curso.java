package com.micolegio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
//MANY TO ONE
@Entity
@Table(name = "tblCursos")
public class Curso {
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idCurso")
	private int id;
	
	@Column(name = "nombreCurso", length = 100, nullable = false)
	private String nombre;
	
	@Column(name = "creditosCurso", length = 100, nullable = false)
	private int creditos;
	
	@ManyToOne
	@JoinColumn(name = "nive_id", nullable = false)
	private Nivel nivel;//FK
	
	//metodos
	public Curso() {
		
	}

	public Curso(int id, String nombre, int creditos, Nivel nivel) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.creditos = creditos;
		this.nivel = nivel;
	}

	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCreditos() {
		return creditos;
	}

	public void setCreditos(int creditos) {
		this.creditos = creditos;
	}

	public Nivel getNivel() {
		return nivel;
	}

	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + ", nivel=" + nivel + "]";
	}
	
	
	
}
