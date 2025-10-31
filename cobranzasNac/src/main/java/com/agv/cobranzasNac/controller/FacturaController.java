package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Factura;
import com.agv.cobranzasNac.service.FacturaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador MVC para gestionar las vistas de Facturas existentes.
 * Permite listar y anular (baja lógica) facturas.
 */
@Controller
@RequestMapping("/facturas") // URL base
public class FacturaController {

    @Autowired
    private FacturaService facturaService; // Inyectamos el servicio CRUD de Factura

    /**
     * Muestra la página principal de facturas.
     * Esta página mostrará la lista de facturas (activas).
     * Escucha en: GET /facturas/listar
     */
    @GetMapping("/listar")
    public String listarFacturas(Model model) {
        
        // 1. Enviamos la lista de facturas activas a la vista
        // (Asumiendo que facturaService.listarTodos() usa findByActivoTrue())
        model.addAttribute("facturas", facturaService.listarTodos());

        // 2. Devolvemos el nombre del archivo HTML (ej: "facturas.html")
        return "facturas"; 
    }
    
    /**
     * Da de baja (Anula) una factura.
     * Escucha en: GET /facturas/anular/{id}
     */
    @GetMapping("/anular/{id}")
    public String anularFactura(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            // facturaService.eliminar() debe implementar la BAJA LÓGICA (activo = false)
            facturaService.eliminar(id); 
            redirectAttributes.addFlashAttribute("guardadoExitoso", "Factura anulada (dada de baja) exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al anular la factura.");
        }

        return "redirect:/facturas/listar";
    }

    /**
     * Muestra la página de detalles de una factura (solo lectura).
     * Escucha en: GET /facturas/ver/{id}
     */
    @GetMapping("/ver/{id}")
    public String verDetalleFactura(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        
        Optional<Factura> facturaOpt = facturaService.buscarPorId(id);

        if (facturaOpt.isPresent()) {
            model.addAttribute("factura", facturaOpt.get());
            // Aquí también podríamos cargar las letras de esa factura para verlas
            // model.addAttribute("letras", facturaOpt.get().getPlanillaLetra().getLetras());
            
            return "detalleFactura"; // Devuelve la vista de detalle
        } else {
            redirectAttributes.addFlashAttribute("error", "Factura no encontrada.");
            return "redirect:/facturas/listar";
        }
    }
}
