package fabrica;

import interfaces.ICursoDAO;
import interfaces.IDocenteDAO;

public class MySqlDAOFactory extends DAOFactory{
	
	@Override
	public ICursoDAO getCursoDAO() {
		return dao.mysql.CursoDAO.getInstancia();
	}

	@Override
	public IDocenteDAO getdDocenteDAO() {
		// TODO Auto-generated method stub
		return dao.mysql.DocenteDAO.getInstancia();
	}
}
