package com.micolegio.app;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	//Atributos
	private static final String PERSISTENCE_UNIT_NAME = "LP2_appMiColegio_Sesion_04PU";
	//no necesito instanciar la clase para usar el metodo
	private static EntityManagerFactory factory;
	
	//Metodos
	public static EntityManagerFactory getEntityManagerFactory() {
		if(factory == null) {
			factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		}
		return factory;
	}
	
	public static void shutdown() {
		if(factory != null) {
			factory.close();
		}
	}
}
