package fabrica;

import dao.oracle.AutoDAO;
import dao.oracle.MarcaDAO;
import interfaces.IAutoDAO;
import interfaces.IMarcaDAO;

public class OracleDAOFactory extends DAOFactory {
	private static OracleDAOFactory instancia;

	public static OracleDAOFactory getInstancia() {
		if (instancia == null) {
			instancia = new OracleDAOFactory();
		}
		return instancia;
	}
	
	private OracleDAOFactory() {}
	
	@Override
	public IAutoDAO getAutoDAO() {
		return AutoDAO.getInstancia();
	}

	@Override
	public IMarcaDAO getMarcaDAO() {
		return MarcaDAO.getInstancia();
	}

}