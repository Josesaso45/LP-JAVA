package interfaces;

import java.util.ArrayList;
import entidades.Chofer;

public interface IChoferDAO {
    public ArrayList<Chofer> listarChoferes();
    public int actualizarEstado(int codigo, int estado);
    public int registrar(Chofer chofer);
    public int actualizar(Chofer chofer);
    public Chofer buscar(int codigo);
}
