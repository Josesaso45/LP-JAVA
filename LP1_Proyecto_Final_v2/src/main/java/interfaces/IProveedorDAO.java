package interfaces;

import java.util.ArrayList;

import entidades.Proveedor;

public interface IProveedorDAO {
	public int crear(Proveedor proveedor);
	public ArrayList<Proveedor> listar();
	public Proveedor obtener(int proveedorId);
	public int actualizar(Proveedor proveedor);
	public int eliminar(int proveedorId);
}
