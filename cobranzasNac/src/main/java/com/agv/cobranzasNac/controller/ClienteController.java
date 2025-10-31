package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Cliente;
import com.agv.cobranzasNac.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes") // URL base para este controlador
public class ClienteController {

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/listar")
    public String listarClientes(Model model) {
        
        model.addAttribute("clientes", clienteService.listarTodos());

        if (!model.containsAttribute("clienteAInsertar")) {
            model.addAttribute("clienteAInsertar", new Cliente());
        }

        return "clientes"; // Devuelve la vista clientes.html
    }


    @PostMapping("/guardar")
    public String guardarCliente(@ModelAttribute("clienteAInsertar") Cliente cliente, 
                                 RedirectAttributes redirectAttributes) {
        
        try {
            clienteService.guardar(cliente);
            redirectAttributes.addFlashAttribute("guardadoExitoso", "Cliente guardado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al guardar el cliente: " + e.getMessage());
        }
        
        return "redirect:/clientes/listar";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, 
                                          RedirectAttributes redirectAttributes) {
        
        Optional<Cliente> clienteOpt = clienteService.buscarPorId(id);

        if (clienteOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("clienteAInsertar", clienteOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("error", "Cliente no encontrado.");
        }

        return "redirect:/clientes/listar";
    }


    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            clienteService.eliminar(id); 
            redirectAttributes.addFlashAttribute("eliminadoExitoso", "Cliente dado de baja exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al dar de baja al cliente.");
        }

        return "redirect:/clientes/listar";
    }
}