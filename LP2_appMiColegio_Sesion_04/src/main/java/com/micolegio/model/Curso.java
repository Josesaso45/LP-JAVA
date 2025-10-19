package com.micolegio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

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
	
	public Curso() {
		
	}
	
	//constructores
	public Curso(int id, String nombre, int creditos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.creditos = creditos;
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

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + ", creditos=" + creditos + "]";
	}
	
	
}
