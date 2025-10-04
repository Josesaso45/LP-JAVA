package fabrica;

import interfaces.ICursoDAO;
import interfaces.IDocenteDAO;

public abstract class DAOFactory {
	
	public static final int MYSQL = 1;
	public static final int ORACLE = 2;
	public static final int SQLSERVER = 3;
	public static final int POSGRESQL = 4;
	
	public static DAOFactory getDaoFactory() {
		return getDaoFactory(MYSQL);//DEFINIR CON QUE TIPO BASE DE DATO TRABAJAR
	}
	
	private static DAOFactory getDaoFactory(int db) {
		switch(db) {
			case MYSQL:
				return MySqlDAOFactory.getInstancia();
			case ORACLE:
				return OracleDAOFactory.getInstancia;
			case SQLSERVER:
				return null;
			default:
				return null;
		}
	}
	//METODOS QUE RETORNAN LOS DAO DE CADA ENTIDAD
	public abstract ICursoDAO getCursoDAO();
	public abstract IDocenteDAO getdDocenteDAO();

}
