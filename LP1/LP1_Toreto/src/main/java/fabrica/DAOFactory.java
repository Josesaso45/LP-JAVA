package fabrica;

import interfaces.IAutoDAO;
import interfaces.IMarcaDAO;

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
			case ORACLE:
				return OracleDAOFactory.getInstancia();
			case SQLSERVER:
				return null;
			case POSGRESQL:
				return null;
			default:
				return null;
		}
	}
	
	public abstract IAutoDAO getAutoDAO();
	public abstract IMarcaDAO getMarcaDAO();
}