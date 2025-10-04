package fabrica;

import interfaces.ICursoDAO;
import interfaces.IDocenteDAO;

public class OracleDAOFactory extends DAOFactory{
	
	@Override
	public ICursoDAO getCursoDAO() {
		return dao.oracle.CursoDAO.getInstancia();
	}

	@Override
	public IDocenteDAO getdDocenteDAO() {
		// TODO Auto-generated method stub
		return dao.oracle.DocenteDAO.getInstancia();
	}
}
