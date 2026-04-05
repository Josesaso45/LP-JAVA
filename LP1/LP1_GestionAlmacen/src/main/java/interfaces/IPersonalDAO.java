package interfaces;


import entidades.Personal;
import java.util.List;

public interface IPersonalDAO {
    int agregar(Personal personal);
    int actualizar(Personal personal);
    int eliminar(int idPersonal);
    Personal obtenerPorId(int idPersonal);
    Personal validarLogin(String username, String password); 
    List<Personal> obtenerTodos();
}
