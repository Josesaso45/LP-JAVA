package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Letra;
import com.agv.cobranzasNac.service.LetraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador MVC para gestionar las vistas de Letras (Cuotas).
 * Permite listar y anular (baja lógica) letras.
 */
@Controller
@RequestMapping("/letras") 
public class LetraController {

    @Autowired
    private LetraService letraService; 


    @GetMapping("/listar")
    public String listarLetras(Model model) {
        
        model.addAttribute("letras", letraService.obtenerTodasLasLetras());

        return "letras"; 
    }
    
    /**
     * Da de baja (Anula) una Letra.
     * Escucha en: GET /letras/anular/{id}
     */
    @GetMapping("/anular/{id}")
    public String anularLetra(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            letraService.eliminarLetra(id); 
            redirectAttributes.addFlashAttribute("guardadoExitoso", "Letra anulada (dada de baja) exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al anular la letra.");
        }

        return "redirect:/letras/listar";
    }

    /**
     * Muestra la página de detalles de una Letra (solo lectura).
     * Escucha en: GET /letras/ver/{id}
     */
    @GetMapping("/ver/{id}")
    public String verDetalleLetra(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        
        Optional<Letra> letraOpt = letraService.obtenerLetraPorId(id);

        if (letraOpt.isPresent()) {
            model.addAttribute("letra", letraOpt.get());
            
            return "detalleLetra"; 
        } else {
            redirectAttributes.addFlashAttribute("error", "Letra no encontrada.");
            return "redirect:/letras/listar";
        }
    }
}