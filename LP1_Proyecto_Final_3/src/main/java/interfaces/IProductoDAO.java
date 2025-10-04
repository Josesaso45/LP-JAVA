package interfaces;

import java.util.ArrayList;

import entidades.Producto;

public interface IProductoDAO {
	public int crear(Producto producto);
	public ArrayList<Producto> listar();
	public Producto obtener(int productoId);
	public int actualizar(Producto producto);
	public int eliminar(int productoId);
	
	public ArrayList<Producto> buscar(String texto);
}
