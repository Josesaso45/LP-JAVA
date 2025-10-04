package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import entidades.Auto;
import entidades.Marca;
import fabrica.DAOFactory;
import interfaces.IAutoDAO;
import interfaces.IMarcaDAO;

@WebServlet(name = "auto", urlPatterns = { "/auto" })
public class AutoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private IAutoDAO autoDAO;
	private IMarcaDAO marcaDAO;
    
    public AutoServlet() {
        super();
        this.autoDAO = DAOFactory.getDAOFactory().getAutoDAO();
        this.marcaDAO = DAOFactory.getDAOFactory().getMarcaDAO();
    }

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	protected void lista(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String texto = "";
		
		if (request.getParameter("texto") != null) {
			texto = request.getParameter("texto");
		}
		
		ArrayList<Auto> lista = this.autoDAO.buscar(texto);
		
		request.setAttribute("lista", lista);				
		request.getRequestDispatcher("/auto/auto_lista.jsp").forward(request, response);
	}
	protected void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Auto auto = new Auto();
		if (request.getParameter("id") != null) {
			int autoId = Integer.parseInt(request.getParameter("id"));
			auto = this.autoDAO.obtener(autoId);
		}
		ArrayList<Marca> marcas = this.marcaDAO.listar();
		
		request.setAttribute("registro", auto);
		request.setAttribute("listaMarcas", marcas);
		request.getRequestDispatcher("/auto/auto_editar.jsp").forward(request, response);
	}
	protected void registrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int autoId = Integer.parseInt(request.getParameter("autoId"));
		String modelo = request.getParameter("modelo");
		int marcaId = Integer.parseInt(request.getParameter("marcaId"));
		String color = request.getParameter("color");
		BigDecimal precio = new BigDecimal(request.getParameter("precio"));
		
		Auto auto = new Auto(autoId, modelo, marcaId, color, precio);
		
		if (auto.getAutoId() == 0) {
			this.autoDAO.crear(auto);
		} else {
			this.autoDAO.actualizar(auto);
		}
		
		response.sendRedirect("auto");		
	}
	protected void eliminar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("id") != null) {
			int autoId = Integer.parseInt(request.getParameter("id"));
			this.autoDAO.eliminar(autoId);
		}
		
		response.sendRedirect("auto");
	}
}