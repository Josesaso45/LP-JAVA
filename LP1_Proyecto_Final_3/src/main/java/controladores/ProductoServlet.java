package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import entidades.Producto;
import entidades.Almacen;
import entidades.Proveedor;
import entidades.Usuario;
import fabrica.DAOFactory;
import interfaces.IProductoDAO;
import interfaces.IAlmacenDAO;
import interfaces.IProveedorDAO;

/**
 * Servlet implementation class CursoServlet
 */
@WebServlet(name = "producto", urlPatterns = { "/producto" })
public class ProductoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IProductoDAO productoDAO;
	private IAlmacenDAO almacenDAO;
	private IProveedorDAO proveedorDAO;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductoServlet() {
        super();
        this.productoDAO = DAOFactory.getDAOFactory().getProductoDAO();
        this.almacenDAO = DAOFactory.getDAOFactory().getAlmacenDAO();
        this.proveedorDAO = DAOFactory.getDAOFactory().getProveedorDAO();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession miSession = request.getSession();
		Usuario authUsuario = new Usuario();
		if (miSession.getAttribute("usuario") != null) authUsuario = (Usuario) miSession.getAttribute("usuario");
		
		if (authUsuario.getUsuarioId() == 0) response.sendRedirect("autenticacion");
		else {
			
			String opcion = "";
			if (request.getParameter("opcion") != null) opcion = request.getParameter("opcion");
			switch (opcion) {
				case "lista" : this.lista(request, response); break;
				case "editar" : this.editar(request, response); break;
				case "registrar" : this.registrar(request, response); break;
				case "eliminar" : this.eliminar(request, response); break;
				default:
					this.lista(request, response);
			}
		}
	}
	
	protected void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String texto = "";
		
		if (request.getParameter("texto") != null) {
			texto = request.getParameter("texto");
		}
		
		ArrayList<Producto> lista = this.productoDAO.buscar(texto);
		
		request.setAttribute("lista", lista);				
		request.getRequestDispatcher("/producto/producto_lista.jsp").forward(request, response);
	}
	protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Producto producto = new Producto();
		if (request.getParameter("id") != null) {
			int productoId = Integer.parseInt(request.getParameter("id"));
			producto = this.productoDAO.obtener(productoId);
		}
		ArrayList<Almacen> almacen = this.almacenDAO.listar();
		ArrayList<Proveedor> proveedor = this.proveedorDAO.listar();
		
		request.setAttribute("registro", producto);
		request.setAttribute("listaAlmacenes", almacen);
		request.setAttribute("listaProveedores", proveedor);
		
		request.getRequestDispatcher("/producto/producto_editar.jsp").forward(request, response);
	}
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int productoId = Integer.parseInt(request.getParameter("productoId"));
		String nombre = request.getParameter("nombre");
		String descripcion = request.getParameter("descripcion");
		int precio = Integer.parseInt(request.getParameter("precio"));
		String stock = request.getParameter("stock");
		int proveedorId = Integer.parseInt(request.getParameter("proveedorId"));
		int almacenId = Integer.parseInt(request.getParameter("almacenId"));
		
		Producto producto = new Producto(productoId, nombre, descripcion, precio, stock, proveedorId, almacenId);
		
		if (producto.getProductoId() == 0) {
			this.productoDAO.crear(producto);
		} else {
			this.productoDAO.actualizar(producto);
		}
		
		response.sendRedirect("producto");		
	}
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int productoId = Integer.parseInt(request.getParameter("id"));
			this.productoDAO.eliminar(productoId);
		}
		
		response.sendRedirect("producto");
	}

}
