package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;

import entidades.Almacen;
import entidades.Usuario;
import fabrica.DAOFactory;
import interfaces.IAlmacenDAO;

/**
 * Servlet implementation class AlmacenServlet
 */
@WebServlet(name = "almacen", urlPatterns = { "/almacen" })
public class AlmacenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private IAlmacenDAO almacenDAO;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlmacenServlet() {
        super();
        this.almacenDAO = DAOFactory.getDAOFactory().getAlmacenDAO();
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
		ArrayList<Almacen> lista = this.almacenDAO.listar();
		
		request.setAttribute("lista", lista);				
		request.getRequestDispatcher("/almacen/almacen_lista.jsp").forward(request, response);
	}
	protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Almacen almacen = new Almacen();
		if (request.getParameter("id") != null) {
			int almacenId = Integer.parseInt(request.getParameter("id"));
			almacen = this.almacenDAO.obtener(almacenId);
		}
		
		request.setAttribute("registro", almacen);
		request.getRequestDispatcher("/almacen/almacen_editar.jsp").forward(request, response);
	}
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int almacenId = Integer.parseInt(request.getParameter("almacenId"));
		String nombre = request.getParameter("nombre");
		String ubicacion = request.getParameter("ubicacion");
		String descripcion = request.getParameter("descripcion");
		
		Almacen almacen = new Almacen(almacenId, nombre, ubicacion, descripcion);

		if (almacen.getAlmacenId() == 0) {
			this.almacenDAO.crear(almacen);
		} else {
			this.almacenDAO.actualizar(almacen);
		}
		
		response.sendRedirect("almacen");
	}
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int almacenId = Integer.parseInt(request.getParameter("id"));
			this.almacenDAO.eliminar(almacenId);
		}
		
		response.sendRedirect("almacen");
	}

}
