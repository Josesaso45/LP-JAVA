package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import entidades.Proveedor;
import entidades.Usuario;
import fabrica.DAOFactory;
import interfaces.IProveedorDAO;

/**
 * Servlet implementation class ProveedorServlet
 */
@WebServlet(name = "proveedor", urlPatterns = { "/proveedor" })
public class ProveedorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IProveedorDAO proveedorDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProveedorServlet() {
        super();
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
		ArrayList<Proveedor> listas = this.proveedorDAO.listar();
		
		request.setAttribute("listas", listas);				
		request.getRequestDispatcher("/proveedor/proveedor_lista.jsp").forward(request, response);
	}
	protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Proveedor proveedor = new Proveedor();
		if (request.getParameter("id") != null) {
			int proveedorId = Integer.parseInt(request.getParameter("id"));
			proveedor = this.proveedorDAO.obtener(proveedorId);
		}
		
		request.setAttribute("registro", proveedor);
		request.getRequestDispatcher("/proveedor/proveedor_editar.jsp").forward(request, response);
	}
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int proveedorId = Integer.parseInt(request.getParameter("proveedorId"));
		String nombre = request.getParameter("nombre");
		String contacto = request.getParameter("contacto");
		String telefono = request.getParameter("telefono");
		String email = request.getParameter("email");
		
		Proveedor proveedor = new Proveedor(proveedorId, nombre, contacto, telefono, email);

		if (proveedor.getProveedorId() == 0) {
			this.proveedorDAO.crear(proveedor);
		} else {
			this.proveedorDAO.actualizar(proveedor);
		}
		
		response.sendRedirect("proveedor");
	}
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int proveedorId = Integer.parseInt(request.getParameter("id"));
			this.proveedorDAO.eliminar(proveedorId);
		}
		
		response.sendRedirect("proveedor");
	}

}
