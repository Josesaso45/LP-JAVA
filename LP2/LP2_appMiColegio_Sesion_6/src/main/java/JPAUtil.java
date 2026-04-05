import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
	//Atributos
	private static final String PERSISTENCE_UNIT_NAME = "LP2_appMiColegio_Sesion_04PU";
	//private static final hace que la variable sea de clase y pueda ser usada en metodos estaticos
	//los metodos estaticos son aquellos que pertenecen a la clase y no a una instancia de la clase
	//la diferencia entre metodos estaticos y no estaticos es que los estaticos pueden ser llamados sin crear una instancia de la clase
	//la diferencia entre una instancia y una clase es que una instancia es un objeto creado a partir de una clase y una clase es una plantilla para crear objetos
	//un objeto es una instancia de una clase
	//una instancia es un objeto creado a partir de una clase
	//de manera muy simple, una clase es un molde y una instancia es un objeto creado a partir de ese molde
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
