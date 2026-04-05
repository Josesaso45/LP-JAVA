package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import dao.MySqlChoferDAO;
import entidades.Chofer;

@WebServlet(name = "ChoferServlet", urlPatterns = { "/chofer" })
public class ChoferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }
        switch (accion) {
            case "listar":
                listarChoferes(request, response);
                break;
            case "nuevo":
                nuevoChofer(request, response);
                break;
            case "registrar":
                registrarChofer(request, response);
                break;
            case "editar":
                editarChofer(request, response);
                break;
            case "actualizar":
                actualizarChofer(request, response);
                break;
            default:
                listarChoferes(request, response);
        }
    }

    private void actualizarChofer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        
        Chofer chofer = new Chofer(codigo, nombre, apellidos, 0); // El estado no se edita aquí
        
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        choferDAO.actualizar(chofer); // Necesitarás crear este método en tu DAO
        
        response.sendRedirect("chofer?accion=listar");
    }

    private void editarChofer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        Chofer chofer = choferDAO.buscar(codigo);
        request.setAttribute("chofer", chofer);
        request.getRequestDispatcher("/Chofer/editarChofer.jsp").forward(request, response);
    }

    private void registrarChofer(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        
        Chofer chofer = new Chofer(0, nombre, apellidos, 0); // El código es autoincremental, el estado inicial es 0 (Libre)
        
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        choferDAO.registrar(chofer); // Necesitarás crear este método en tu DAO
        
        response.sendRedirect("chofer?accion=listar");
    }

    private void nuevoChofer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/Chofer/registroChofer.jsp").forward(request, response);
    }

    private void listarChoferes(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        ArrayList<Chofer> lista = choferDAO.listarChoferes();
        request.setAttribute("listaChoferes", lista);
        request.getRequestDispatcher("/Chofer/listadoChofer.jsp").forward(request, response);
    }
}