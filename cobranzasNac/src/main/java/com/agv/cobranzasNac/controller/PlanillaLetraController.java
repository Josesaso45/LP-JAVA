package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.PlanillaLetra;

import com.agv.cobranzasNac.service.PlanillaLetraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador MVC para gestionar las vistas de Planillas de Letras.
 * Permite listar y anular (baja lógica) planillas.
 */
@Controller
@RequestMapping("/planillas") // URL base
public class PlanillaLetraController {

    @Autowired
    private PlanillaLetraService planillaLetraService; // Inyectamos el servicio CRUD

    /**
     * Muestra la página principal de Planillas.
     * Esta página mostrará la lista de planillas activas.
     * Escucha en: GET /planillas/listar
     */
    @GetMapping("/listar")
    public String listarPlanillas(Model model) {
        
        // 1. Enviamos la lista de planillas activas a la vista
        model.addAttribute("planillas", planillaLetraService.listarTodos());

        // 2. Devolvemos el nombre del archivo HTML (ej: "planillas.html")
        return "planillas"; 
    }
    
    /**
     * Da de baja (Anula) una Planilla.
     * Escucha en: GET /planillas/anular/{id}
     */
    @GetMapping("/anular/{id}")
    public String anularPlanilla(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            // Este servicio ya implementa la BAJA LÓGICA
            planillaLetraService.eliminar(id); 
            redirectAttributes.addFlashAttribute("guardadoExitoso", "Planilla anulada (dada de baja) exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al anular la planilla.");
        }

        return "redirect:/planillas/listar";
    }

    /**
     * Muestra la página de detalles de una Planilla (solo lectura).
     * Escucha en: GET /planillas/ver/{id}
     */
    @GetMapping("/ver/{id}")
    public String verDetallePlanilla(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        
        Optional<PlanillaLetra> planillaOpt = planillaLetraService.buscarPorId(id);

        if (planillaOpt.isPresent()) {
            PlanillaLetra planilla = planillaOpt.get();
            model.addAttribute("planilla", planilla);
            
            // También enviamos las letras que pertenecen a esta planilla
            model.addAttribute("letrasDeLaPlanilla", planilla.getLetras());
            
            // Devuelve el nombre de la vista de detalle
            return "detallePlanilla"; 
        } else {
            redirectAttributes.addFlashAttribute("error", "Planilla no encontrada.");
            return "redirect:/planillas/listar";
        }
    }
}