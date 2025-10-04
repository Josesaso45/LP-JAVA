package fabrica;

import interfaces.IAlmacenDAO;
import interfaces.IProductoDAO;
import interfaces.IProveedorDAO;
import interfaces.IUsuarioDAO;

public abstract class DAOFactory {

	public static final int MYSQL = 1;
	public static final int ORACLE = 2;
	public static final int SQLSERVER = 3;
	public static final int POSGRESQL = 4;
	
	public static DAOFactory getDAOFactory() {
		return getDAOFactory(MYSQL); 
	}
	
	private static DAOFactory getDAOFactory(int db) {
		switch (db) {
			case MYSQL:
				return MySqlDAOFactory.getInstancia();
			case SQLSERVER:
				return null;
			case POSGRESQL:
				return null;
			default:
				return null;
		}
	}
	
	public abstract IProductoDAO getProductoDAO();
	public abstract IProveedorDAO getProveedorDAO();
	public abstract IAlmacenDAO getAlmacenDAO();
	public abstract IUsuarioDAO getUsuarioDAO();

}
