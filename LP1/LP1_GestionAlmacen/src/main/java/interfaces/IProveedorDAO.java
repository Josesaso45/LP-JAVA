package interfaces;

import entidades.Proveedor;

import java.util.List;

public interface IProveedorDAO {
    int agregar(Proveedor proveedor);
    int actualizar(Proveedor proveedor);
    int eliminar(int idProveedor);
    Proveedor obtenerPorId(int idProveedor);
    List<Proveedor> obtenerTodos();
}

