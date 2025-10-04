package interfaces;

import java.util.ArrayList;
import entidades.Asignacion;

public interface IAsignacionDAO {
    public int registrar(Asignacion a);
    public int editar(Asignacion a);
    public int eliminar(int numero);
    public ArrayList<Asignacion> listarAsignaciones();
    public Asignacion buscar(int numero);
}
