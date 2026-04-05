package fabrica;

import dao.mysql.ProductoDAO;
import interfaces.IProductoDAO;
import interfaces.IProveedorDAO;
import interfaces.IUbicacionAlmacenDAO;
import interfaces.IPersonalDAO;

class MySqlDAOFactory extends DAOFactory {

    @Override
    public IProductoDAO getProductoDAO() {
        return new ProductoDAO();
    }

    @Override
    public IProveedorDAO getProveedorDAO() {
        return new Proveedor(); // 
    }

    @Override
    public IUbicacionAlmacenDAO getUbicacionAlmacenDAO() {
        return new UbicacionAlmacenDAO(); //
    }

    @Override
    public IPersonalDAO getPersonalDAO() {
        return new PersonalDAO(); // Deberás crear esta clase
    }
}
