package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Pedido;
import com.agv.cobranzasNac.service.PedidoService;
import com.agv.cobranzasNac.service.VendedorService; 
import com.agv.cobranzasNac.service.ClienteService; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 
import java.util.Optional;

@Controller
@RequestMapping("/pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;
    
    @Autowired
    private VendedorService vendedorService;
    
    @Autowired
    private ClienteService clienteService; 
    
    @GetMapping("/listar")
    public String listarPedidos(Model model) {
        
        model.addAttribute("pedidos", pedidoService.listarTodos());

        if (!model.containsAttribute("pedidoAInsertar")) {
            model.addAttribute("pedidoAInsertar", new Pedido());
        }
        
        model.addAttribute("vendedores", vendedorService.listarTodos());
        model.addAttribute("clientes", clienteService.listarTodos());
        return "pedidos"; 
    }

    
    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedidoAInsertar") Pedido pedido, 
                                 RedirectAttributes redirectAttributes) {
        pedidoService.guardar(pedido);
        redirectAttributes.addFlashAttribute("guardadoExitoso", "Pedido guardado exitosamente");
        return "redirect:/pedidos/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, 
                                          RedirectAttributes redirectAttributes) {
        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(id);
        if (pedidoOpt.isPresent()) {
            redirectAttributes.addFlashAttribute("pedidoAInsertar", pedidoOpt.get());
        } else {
            redirectAttributes.addFlashAttribute("error", "Pedido no encontrado.");
        }
        return "redirect:/pedidos/listar";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            pedidoService.eliminar(id); 
            redirectAttributes.addFlashAttribute("eliminadoExitoso", "Pedido eliminado (dado de baja) exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al dar de baja el pedido.");
        }
        return "redirect:/pedidos/listar";
    }
}