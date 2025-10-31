package com.agv.cobranzasNac.controller;

import com.agv.cobranzasNac.model.Pedido;
import com.agv.cobranzasNac.service.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Para enviar datos a la vista
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // Para mensajes

import java.util.Optional;


@Controller
@RequestMapping("/pedidos") 
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/listar")
    public String listarPedidos(Model model) {
        
        // 1. Enviamos la lista de pedidos a la vista
        model.addAttribute("pedidos", pedidoService.listarTodos());

        // 2. Enviamos un objeto 'Pedido' vacío para el formulario de "nuevo"
        // (Si 'pedidoAInsertar' no existe en el modelo, lo creamos)
        if (!model.containsAttribute("pedidoAInsertar")) {
            model.addAttribute("pedidoAInsertar", new Pedido());
        }

        // 3. Devolvemos el nombre del archivo HTML (ej: "pedidos.html")
        return "pedidos"; 
    }

    /**
     * Guarda un pedido (nuevo o actualizado) desde el formulario.
     * Escucha en: POST /pedidos/guardar
     */
    @PostMapping("/guardar")
    public String guardarPedido(@ModelAttribute("pedidoAInsertar") Pedido pedido, 
                                RedirectAttributes redirectAttributes) {
        
        pedidoService.guardar(pedido);
        
        // Añadimos un mensaje de éxito que sobrevivirá al redirect
        redirectAttributes.addFlashAttribute("guardadoExitoso", "Pedido guardado exitosamente");

        // Redirigimos al usuario de vuelta a la lista
        return "redirect:/pedidos/listar";
    }

    /**
     * Prepara el formulario para ACTUALIZAR un pedido.
     * Carga los datos del pedido en el formulario.
     * Escucha en: GET /pedidos/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, 
                                          RedirectAttributes redirectAttributes) {
        
        Optional<Pedido> pedidoOpt = pedidoService.buscarPorId(id);

        if (pedidoOpt.isPresent()) {
            // Si lo encuentra, lo pone en el modelo usando 'RedirectAttributes'
            // para que esté disponible después del redirect
            redirectAttributes.addFlashAttribute("pedidoAInsertar", pedidoOpt.get());
        } else {
            // Si no lo encuentra, manda un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Pedido no encontrado.");
        }

        // Siempre redirigimos a /listar. 
        // El método listarPedidos() detectará 'pedidoAInsertar' y poblará el formulario.
        return "redirect:/pedidos/listar";
    }

    /**
     * Elimina un pedido.
     * Escucha en: GET /pedidos/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarPedido(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            pedidoService.eliminar(id);
            redirectAttributes.addFlashAttribute("eliminadoExitoso", "Pedido eliminado exitosamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el pedido.");
        }

        return "redirect:/pedidos/listar";
    }
}