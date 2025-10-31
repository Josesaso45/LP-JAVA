package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Vendedor;
import com.agv.cobranzasNac.service.VendedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/vendedores") // URL base para este controlador
public class VendedorController {

    @Autowired
    private VendedorService vendedorService;

    /**
     * Muestra la página de gestión de Vendedores (formulario y lista).
     */
    @GetMapping("/listar")
    public String listarVendedores(Model model) {
        
        model.addAttribute("vendedores", vendedorService.listarTodos());

        if (!model.containsAttribute("vendedorAInsertar")) {
            model.addAttribute("vendedorAInsertar", new Vendedor());
        }

        return "vendedores"; 
    }

    /**
     * Guarda un vendedor (nuevo o actualizado).
     */
    @PostMapping("/guardar")
    public String guardarVendedor(@ModelAttribute("vendedorAInsertar") Vendedor vendedor, 
                                 RedirectAttributes redirectAttributes) {
        
        try {
            vendedorService.guardar(vendedor);
            redirectAttributes.addFlashAttribute("guardadoExitoso", "Vendedor guardado exitosamente");
        } catch (Exception e) {
            // Captura de error (ej: si el 'codigo' ya existe)
            redirectAttributes.addFlashAttribute("error", "Error al guardar el vendedor: " + e.getMessage());
        }
        
        return "redirect:/vendedores/listar";
    }

    /**
     * Prepara el formulario para ACTUALIZAR un vendedor.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, 
                                          RedirectAttributes redirectAttributes) {
        
        Optional<Vendedor> vendedorOpt = vendedorService.buscarPorId(id);

        if (vendedorOpt.isPresent()) {
            // Si lo encuentra, lo pasa al formulario
            redirectAttributes.addFlashAttribute("vendedorAInsertar", vendedorOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("error", "Vendedor no encontrado.");
        }

        return "redirect:/vendedores/listar";
    }

    /**
     * Elimina (Baja Lógica) un vendedor.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarVendedor(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            vendedorService.eliminar(id); // (Este ya hace la baja lógica)
            redirectAttributes.addFlashAttribute("eliminadoExitoso", "Vendedor dado de baja exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al dar de baja al vendedor.");
        }

        return "redirect:/vendedores/listar";
    }
}