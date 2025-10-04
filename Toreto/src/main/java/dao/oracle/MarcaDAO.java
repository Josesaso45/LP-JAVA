package dao.oracle;

import java.util.ArrayList;

import entidades.Marca;
import interfaces.IMarcaDAO;

public class MarcaDAO implements IMarcaDAO {
	private static IMarcaDAO instancia;
	
	public static IMarcaDAO getInstancia() {
		if (instancia == null) {
			instancia = new MarcaDAO();
		}
		return instancia;
	}
	
	private MarcaDAO() {}
	
	@Override
	public int crear(Marca marca) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public ArrayList<Marca> listar() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Marca obtener(int marcaId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int actualizar(Marca marca) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int eliminar(int marcaId) {
		// TODO Auto-generated method stub
		return 0;
	}
}