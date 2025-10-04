package interfaces;

import java.util.ArrayList;

import entidades.Auto;

public interface IAutoDAO {
	
	public int crear(Auto auto);
	public ArrayList<Auto> listar();
	public Auto obtener(int autoId);
	public int actualizar(Auto auto);
	public int eliminar(int autoId);
	
	public ArrayList<Auto> buscar(String texto);
}
