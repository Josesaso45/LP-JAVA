package dao.oracle;

import java.math.BigDecimal;
import java.util.ArrayList;

import entidades.Auto;
import interfaces.IAutoDAO;

public class AutoDAO implements IAutoDAO {
	private static IAutoDAO instancia;
	
	public static IAutoDAO getInstancia() {
		if (instancia == null) {
			instancia = new AutoDAO();
		}
		return instancia;
	}
	
	private AutoDAO() {}
	
	@Override
	public int crear(Auto auto) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Auto> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Auto obtener(int autoId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int actualizar(Auto auto) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int eliminar(int autoId) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Auto> buscar(String texto) {
		// TODO Auto-generated method stub
		return null;
	}
}