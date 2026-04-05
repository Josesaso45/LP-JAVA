package interfaces;

import java.util.ArrayList;

import entidades.Marca;

public interface IMarcaDAO {
	public int crear(Marca marca);
	public ArrayList<Marca> listar();
	public Marca obtener(int marcaId);
	public int actualizar(Marca marca);
	public int eliminar(int marcaId);
}