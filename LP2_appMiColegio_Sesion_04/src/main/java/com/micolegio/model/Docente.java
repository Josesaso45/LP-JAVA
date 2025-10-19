package com.micolegio.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblDocentes")
public class Docente {
	//atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDocente", length = 100, nullable = false)
	private int id;
	
	@Column(name = "nombreDocente", length = 100, nullable = false)
	private String nombre;
	
	@Column(name = "sueldoDocente", length = 100, nullable = false)
	private double Sueldo;
	
	@Column(name = "fechaNacimientoDocente", updatable = false, unique = true)
	private LocalDate fechaNacimiento;
	
	public Docente() {
		
	}

	public Docente(int id, String nombre, double sueldo, LocalDate fechaNacimiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		Sueldo = sueldo;
		this.fechaNacimiento = fechaNacimiento;
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

	public double getSueldo() {
		return Sueldo;
	}

	public void setSueldo(double sueldo) {
		Sueldo = sueldo;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	@Override
	public String toString() {
		return "Docente [id=" + id + ", nombre=" + nombre + ", Sueldo=" + Sueldo + ", fechaNacimiento="
				+ fechaNacimiento + "]";
	}
	
	
}
