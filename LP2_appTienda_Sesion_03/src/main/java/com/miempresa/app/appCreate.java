package com.miempresa.app;

import com.miempresa.model.Empleado;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class appCreate {

	public static void main(String[] args) {
		System.out.println("Registro de Empleado");
		//CREACION DE PERSISTENCIA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("LP2_appTienda_Sesion_03PU");
		EntityManager em = emf.createEntityManager();
	
		//CREACION DE OBJETOS
		Empleado objEmp1 = new Empleado();
		
		objEmp1.setNombre("Juan Perez");
		objEmp1.setEmail("juanpe@gmail.com");
		objEmp1.setSalario(2500);
	
		//GUARDAR OBJETOS
		em.getTransaction().begin();
		em.persist(objEmp1);
		em.getTransaction().commit();
		
		//CIERRE DE PERSISTENCIA
		
		System.out.println("Empleado registrado con exito");
	}

}
