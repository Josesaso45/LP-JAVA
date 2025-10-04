package interfaces;


import entidades.UbicacionAlmacen;
import java.util.List;

public interface IUbicacionAlmacenDAO {
    int agregar(UbicacionAlmacen ubicacion);
    int actualizar(UbicacionAlmacen ubicacion);
    int eliminar(int idUbicacion);
    UbicacionAlmacen obtenerPorId(int idUbicacion);
    List<UbicacionAlmacen> obtenerTodos();
}