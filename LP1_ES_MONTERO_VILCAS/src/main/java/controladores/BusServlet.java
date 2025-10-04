package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import dao.MySqlBusDAO;
import entidades.Bus;

@WebServlet(name = "BusServlet", urlPatterns = { "/bus" })
public class BusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar";
        }
        switch (accion) {
            case "listar":
                listarBuses(request, response);
                break;
            // Aquí puedes añadir los casos para "nuevo", "registrar", "editar", "actualizar"
            default:
                listarBuses(request, response);
        }
    }

    private void listarBuses(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySqlBusDAO busDAO = new MySqlBusDAO();
        ArrayList<Bus> lista = busDAO.listarBuses();
        request.setAttribute("listaBuses", lista);
        request.getRequestDispatcher("/Bus/listadoBus.jsp").forward(request, response);
    }
}