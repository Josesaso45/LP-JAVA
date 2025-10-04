package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;

import dao.MySqlAsignacionDAO;
import dao.MySqlBusDAO;
import dao.MySqlChoferDAO;
import entidades.Asignacion;
import entidades.Bus;
import entidades.Chofer;

@WebServlet(name = "AsignacionServlet", urlPatterns = { "/asignacion" })
public class AsignacionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AsignacionServlet() {
        super();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion == null) {
            accion = "listar"; // Acción por defecto
        }

        switch (accion) {
            case "listar":
                listarAsignaciones(request, response);
                break;
            case "cargarFormulario":
                cargarFormulario(request, response);
                break;
            case "registrar":
                registrarAsignacion(request, response);
                break;
            case "eliminar":
                eliminarAsignacion(request, response);
                break;
            default:
                listarAsignaciones(request, response);
        }
    }

    private void eliminarAsignacion(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int numero = Integer.parseInt(request.getParameter("numero"));
        
        MySqlAsignacionDAO asignacionDAO = new MySqlAsignacionDAO();
        asignacionDAO.eliminar(numero); // El SP se encarga de liberar al chofer
        
        actualizarChoferesEnSesion(request);
        
        response.sendRedirect("asignacion?accion=listar&mensajeExito=Asignación eliminada correctamente.");
    }

    private void registrarAsignacion(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int codigoChofer = Integer.parseInt(request.getParameter("codigo_chofer"));
        String placaBus = request.getParameter("placa_bus");
        String fechaStr = request.getParameter("fecha");

        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        Chofer chofer = choferDAO.buscar(codigoChofer);

        if (chofer != null && chofer.getEstado() == 1) {
            request.setAttribute("mensajeError", "El chofer seleccionado ya está asignado.");
            cargarFormulario(request, response);
            return;
        }

        Asignacion nuevaAsignacion = new Asignacion();
        nuevaAsignacion.setCodigo_chofer(codigoChofer);
        nuevaAsignacion.setPlaca_bus(placaBus);
        nuevaAsignacion.setFecha(fechaStr);

        MySqlAsignacionDAO asignacionDAO = new MySqlAsignacionDAO();
        int resultado = asignacionDAO.registrar(nuevaAsignacion);

        if (resultado > 0) {
            choferDAO.actualizarEstado(codigoChofer, 1);
            actualizarChoferesEnSesion(request);
            response.sendRedirect("asignacion?accion=listar&mensajeExito=Asignación registrada correctamente.");
        } else {
            request.setAttribute("mensajeError", "No se pudo registrar la asignación.");
            cargarFormulario(request, response);
        }
    }

    private void cargarFormulario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        MySqlBusDAO busDAO = new MySqlBusDAO();

        ArrayList<Chofer> choferes = choferDAO.listarChoferes();
        ArrayList<Bus> buses = busDAO.listarBuses();

        request.setAttribute("listaChoferesParaCombo", choferes);
        request.setAttribute("listaBusesParaCombo", buses);
        
        request.getRequestDispatcher("/Asignacion/registroAsignacion.jsp").forward(request, response);
    }

    private void listarAsignaciones(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MySqlAsignacionDAO asignacionDAO = new MySqlAsignacionDAO();
        
        // Aquí deberías usar el método que lista con JOIN para tener los nombres
        ArrayList<Asignacion> listaAsignaciones = asignacionDAO.listarAsignacionesConNombres();
        request.setAttribute("listaAsignaciones", listaAsignaciones);
        
        request.getRequestDispatcher("/Transporte/ListadoTransporte.jsp").forward(request, response);
    }
    
    private void actualizarChoferesEnSesion(HttpServletRequest request) {
        MySqlChoferDAO choferDAO = new MySqlChoferDAO();
        ArrayList<Chofer> listaActualizada = choferDAO.listarChoferes();
        
        HttpSession session = request.getSession();
        session.setAttribute("listaChoferes", listaActualizada);
    }
}
