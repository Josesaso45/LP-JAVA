package interfaces;

import java.util.ArrayList;
import entidades.Bus;

public interface IBusDAO {
    public ArrayList<Bus> listarBuses();
    public int registrar(Bus bus);
    public int actualizar(Bus bus);
    public Bus buscar(String placa);
}
