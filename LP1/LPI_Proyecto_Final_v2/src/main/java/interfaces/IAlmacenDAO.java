package interfaces;
import java.util.ArrayList;
import entidades.Almacen;

public interface IAlmacenDAO {
	public int crear(Almacen almacen);
	public ArrayList<Almacen> listar();
	public Almacen obtener(int almacenId);
	public int actualizar(Almacen almacen);
	public int eliminar(int almacenId);
}




