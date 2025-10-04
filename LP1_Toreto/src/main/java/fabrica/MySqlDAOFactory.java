package fabrica;

import dao.mysql.AutoDAO;
import dao.mysql.MarcaDAO;
import interfaces.IAutoDAO;
import interfaces.IMarcaDAO;

public class MySqlDAOFactory extends DAOFactory {
	private static MySqlDAOFactory instancia;

	public static MySqlDAOFactory getInstancia() {
		if (instancia == null) {
			instancia = new MySqlDAOFactory();
		}
		return instancia;
	}
	
	private MySqlDAOFactory() {}
	
	@Override
	public IAutoDAO getAutoDAO() {
		return AutoDAO.getInstancia();
	}

	@Override
	public IMarcaDAO getMarcaDAO() {
		return MarcaDAO.getInstancia();
	}

}