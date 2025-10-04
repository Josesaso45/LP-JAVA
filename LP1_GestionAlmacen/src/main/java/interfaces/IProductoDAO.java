package interfaces;

import entidades.Producto;

import java.util.List;

public interface IProductoDAO {
    int agregar(Producto producto);
    int actualizar(Producto producto);
    int eliminar(int idProducto);
    Producto obtenerPorId(int idProducto);
    List<Producto> obtenerTodos();
}