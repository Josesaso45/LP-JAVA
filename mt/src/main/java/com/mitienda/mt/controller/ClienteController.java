package com.mitienda.mt.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mitienda.mt.model.Cliente;
import com.mitienda.mt.service.ClienteService;
import com.mitienda.mt.service.TipoClienteService;

@Controller
public class ClienteController {

    @Autowired
    private TipoClienteService tipoClienteService;

    @Autowired
    private ClienteService clienteService;


    @GetMapping("/clientes/registro")
    public String mostrarFormularioRegistro(Model model) {
        
        model.addAttribute("cliente", new Cliente());
        
        model.addAttribute("tipos", tipoClienteService.listarTiposDeCliente());
        
        return "registroCliente"; 
    }


    @PostMapping("/clientes/registrar")
    public String registrarCliente(@ModelAttribute Cliente cliente) {
        
        clienteService.registrarCliente(cliente);
        
        return "redirect:/clientes/registro"; 
    }


    @GetMapping("/clientes/consultar")
    public String consultarClientes(
            @RequestParam(name = "tipoCliente", required = false) Integer idTipo, 
            Model model) {
        
        if (idTipo == null) {
            // Si no se envió un ID (ej. se entró por URL directa),
            // mostramos una lista vacía para que la página cargue sin error.
            model.addAttribute("clientes", new ArrayList<Cliente>());
        } else {
            // Si se envió un ID (desde el formulario), filtramos.
            model.addAttribute("clientes", clienteService.listarClientesPorTipo(idTipo));
        }
        
        return "listadoCliente"; // Llama a listadoCliente.html
    }
}