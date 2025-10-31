package com.agv.cobranzasNac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    /**
     * Mapea la raíz del sitio (ej: http://localhost:8080/)
     * para mostrar la página de inicio.
     */
    @GetMapping("/")
    public String mostrarInicio() {
        // En el futuro, aquí inyectarías servicios
        // para buscar KPIs y los pasarías al Model.
        // model.addAttribute("kpiTotalVencido", 15000.00);
        
        return "inicio"; // Devuelve la vista inicio.html
    }
}