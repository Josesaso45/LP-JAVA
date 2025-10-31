package com.agv.cobranzasNac.controller;


import com.agv.cobranzasNac.service.FacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controlador para manejar acciones complejas de facturación,
 * como la generación de facturas y letras desde un pedido.
 */
@Controller
@RequestMapping("/facturacion") // URL base para este controlador
public class FacturacionController {

    @Autowired
    private FacturacionService facturacionService; // Inyectamos el servicio "cerebro"

    /**
     * Procesa un Pedido para generar su Factura y Letras (si aplica).
     * Se activa desde un botón/enlace en la lista de pedidos.
     * Escucha en: GET /facturacion/procesar/{id}
     *
     * @param id El ID del Pedido a procesar.
     * @param redirectAttributes Para enviar mensajes de vuelta a la vista.
     * @return Redirige a la lista de pedidos.
     */
    @GetMapping("/procesar/{id}")
    public String procesarPedidoAFactura(@PathVariable Long id, 
                                       RedirectAttributes redirectAttributes) {
        
        try {
            // 1. Llama al servicio "cerebro" donde está toda la lógica
            facturacionService.procesarPedido(id);
            
            // 2. Si todo sale bien, manda un mensaje de éxito
            redirectAttributes.addFlashAttribute("guardadoExitoso", 
                "Pedido ID " + id + " procesado y facturado exitosamente.");

        } catch (RuntimeException e) {
            // 3. Si algo falla (ej: pedido no encontrado, ya facturado),
            //    captura el error y manda un mensaje
            redirectAttributes.addFlashAttribute("error", 
                "Error al procesar el Pedido ID " + id + ": " + e.getMessage());
        
        } catch (Exception e) {
            // 4. Captura de error genérico
            redirectAttributes.addFlashAttribute("error", 
                "Ocurrió un error inesperado: " + e.getMessage());
        }

        // 5. Pase lo que pase, redirige al usuario a la lista de pedidos
        return "redirect:/pedidos/listar";
    }
}
