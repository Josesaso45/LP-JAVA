package controladores;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;


import dao.MySqlChoferDAO;
import entidades.Chofer;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "login", urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
     * Maneja las peticiones POST del formulario de login.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String clave = request.getParameter("clave");

        // 1. Simulación de validación de credenciales
        if ("admin".equals(usuario) && "123".equals(clave)) {
            System.out.println("Login exitoso para: " + usuario);
            
            // 2. Si el login es correcto, obtenemos la lista de choferes
            MySqlChoferDAO choferDAO = new MySqlChoferDAO();
            ArrayList<Chofer> listaChoferes = choferDAO.listarChoferes();
            
            // 3. Guardamos la lista en la sesión del usuario
            HttpSession session = request.getSession();
            session.setAttribute("listaChoferes", listaChoferes);
            
            // 4. Enviamos una respuesta de éxito (código 200 OK). 
            response.setStatus(HttpServletResponse.SC_OK);
            
        } else {
            System.out.println("Login fallido para: " + usuario);
            
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
            response.getWriter().write("Usuario o contraseña incorrectos.");
        }
    }
}
