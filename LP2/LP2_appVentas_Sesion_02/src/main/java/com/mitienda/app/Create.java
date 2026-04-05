package com.mitienda.app;

import com.mitienda.model.Producto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Create {

	public static void main(String[] args) {
		System.out.println("Creando la BD con Hibernate");
		
		//Inicializar JPA
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("LP2_appVentas_Sesion_02");
		EntityManager em = emf.createEntityManager();
		
		//Crear los objetos
		Producto objPro = new Producto();
		objPro.setNombre("Aceite");
		objPro.setPrecio(176.00);
		
		//Hacer persistentes los objetos
		em.getTransaction().begin();
		em.persist(objPro);
		em.getTransaction().commit();
		
		System.out.println("Objeto registrado con exito");
	}

}
