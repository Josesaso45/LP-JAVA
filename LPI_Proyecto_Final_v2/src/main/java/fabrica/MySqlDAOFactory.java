package fabrica;

import interfaces.IAlmacenDAO;
import interfaces.IProductoDAO;
import interfaces.IProveedorDAO;
import interfaces.IUsuarioDAO;


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
	public IAlmacenDAO getAlmacenDAO() {
		return dao.mysql.AlmacenDAO.getInstancia();
	}

	@Override
	public IProveedorDAO getProveedorDAO() {
		return dao.mysql.ProveedorDAO.getInstancia();
	}

	@Override
	public IProductoDAO getProductoDAO() {
		return dao.mysql.ProductoDAO.getInstancia();
	}

	@Override
	public IUsuarioDAO getUsuarioDAO() {
		return dao.mysql.UsuarioDAO.getInstancia();
	}
}
