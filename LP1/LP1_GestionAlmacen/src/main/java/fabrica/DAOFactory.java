package fabrica;

import interfaces.IPersonalDAO;
import interfaces.IProductoDAO;
import interfaces.IProveedorDAO;
import interfaces.IUbicacionAlmacenDAO;

public abstract class DAOFactory {
    
    // Tipos de base de datos soportadas
    public static final int MYSQL = 1;
    public static final int ORACLE = 2; 
    // ... otros tipos si los hubiera

    // Métodos abstractos para obtener los DAOs de cada entidad
    public abstract IProductoDAO getProductoDAO();
    public abstract IProveedorDAO getProveedorDAO();
    public abstract IUbicacionAlmacenDAO getUbicacionAlmacenDAO();
    public abstract IPersonalDAO getPersonalDAO();
    
    /**
     * Devuelve la factoría específica para el tipo de base de datos.
     * @param tipo El tipo de BD (ej. DAOFactory.MYSQL)
     * @return Una instancia de la factoría concreta.
     */
    public static MySqlDAOFactory getDAOFactory(int tipo) {
        switch (tipo) {
            case MYSQL:
                return new MySqlDAOFactory();
            case ORACLE:
            default:
            	
                return null;
        }
    }
}
