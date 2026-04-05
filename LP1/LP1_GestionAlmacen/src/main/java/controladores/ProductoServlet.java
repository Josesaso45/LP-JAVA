package controladores;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import entidades.Producto;
import fabrica.DAOFactory;
import interfaces.IProductoDAO;

/**
 * Servlet implementation class ProductoServlet
 * Este servlet controla todas las acciones relacionadas con los productos.
 */
@WebServlet("/producto")
public class ProductoServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se obtiene la opción solicitada por el usuario (listar, nuevo, editar)
        String opcion = request.getParameter("opcion");

        if (opcion == null || opcion.isEmpty()) {
            opcion = "listar"; // Opción por defecto
        }

        switch (opcion) {
            case "listar":
                listarProductos(request, response);
                break;
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;
            case "editar":
                mostrarFormularioEditar(request, response);
                break;
            case "eliminar":
                eliminarProducto(request, response);
                break;
            default:
                listarProductos(request, response);
                break;
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se obtiene la opción para saber si es un registro nuevo o una actualización
        String opcion = request.getParameter("opcion");

        if ("guardar".equals(opcion)) {
            guardarProducto(request, response);
        } else {
            listarProductos(request, response);
        }
    }

    private void listarProductos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Obtener la factoría de DAOs para MySQL
        DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        // 2. Obtener el DAO de Producto
        IProductoDAO productoDAO = fabrica.getProductoDAO();
        // 3. Obtener la lista de todos los productos
        List<Producto> listaProductos = productoDAO.obtenerTodos();
        // 4. Guardar la lista en el request para pasarla a la vista
        request.setAttribute("listaProductos", listaProductos);
        // 5. Redirigir a la página JSP que mostrará la lista
        request.getRequestDispatcher("/producto/producto_lista.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simplemente redirige al formulario para crear un nuevo producto
        request.getRequestDispatcher("/producto/producto_editar.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        
        DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        IProductoDAO productoDAO = fabrica.getProductoDAO();
        Producto producto = productoDAO.obtenerPorId(idProducto);
        
        request.setAttribute("producto", producto);
        request.getRequestDispatcher("/producto/producto_editar.jsp").forward(request, response);
    }

    private void guardarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Se obtienen los datos del formulario
        String idProductoStr = request.getParameter("id_producto");
        String nombre = request.getParameter("nombre_producto");
        String descripcion = request.getParameter("descripcion");
        BigDecimal precio = new BigDecimal(request.getParameter("precio_unitario"));
        int stock = Integer.parseInt(request.getParameter("cantidad_stock"));
        int idProveedor = Integer.parseInt(request.getParameter("id_proveedor"));
        int idUbicacion = Integer.parseInt(request.getParameter("id_ubicacion"));

        Producto producto = new Producto();
        producto.setNombre_producto(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecio_unitario(precio);
        producto.setCantidad_stock(stock);
        producto.setId_proveedor(idProveedor);
        producto.setId_ubicacion(idUbicacion);

        DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        IProductoDAO productoDAO = fabrica.getProductoDAO();

        if (idProductoStr == null || idProductoStr.isEmpty()) {
            // Es un producto nuevo
            productoDAO.agregar(producto);
        } else {
            // Es una actualización
            producto.setId_producto(Integer.parseInt(idProductoStr));
            productoDAO.actualizar(producto);
        }
        
        // Redirigir a la lista de productos
        response.sendRedirect(request.getContextPath() + "/ProductoServlet?opcion=listar");
    }

    private void eliminarProducto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idProducto = Integer.parseInt(request.getParameter("id"));
        
        DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        IProductoDAO productoDAO = fabrica.getProductoDAO();
        productoDAO.eliminar(idProducto);
        
        // Redirigir a la lista de productos
        response.sendRedirect(request.getContextPath() + "/ProductoServlet?opcion=listar");
    }
}
