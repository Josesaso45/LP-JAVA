package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.model.Factura;
import com.agv.cobranzasNac.model.Letra;
import com.agv.cobranzasNac.model.Pedido;
import com.agv.cobranzasNac.model.PlanillaLetra;
import com.agv.cobranzasNac.repository.FacturaRepository;
import com.agv.cobranzasNac.repository.LetraRepository;
import com.agv.cobranzasNac.repository.PedidoRepository;
import com.agv.cobranzasNac.repository.PlanillaLetraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de la lógica de negocio compleja
 * que involucra múltiples entidades, como la creación
 * de facturas y la generación de letras.
 */
@Service
public class FacturacionService {

    private final LetraRepository letraRepository;

    // 1. Inyectamos TODOS los repositorios que necesitamos para la operación
    private final PedidoRepository pedidoRepository;
    private final FacturaRepository facturaRepository;
    private final PlanillaLetraRepository planillaLetraRepository;
    // Nota: No necesitamos LetraRepository porque usaremos CascadeType.ALL

    @Autowired
    public FacturacionService(PedidoRepository pedidoRepository, 
                              FacturaRepository facturaRepository, 
                              PlanillaLetraRepository planillaLetraRepository, LetraRepository letraRepository) {
        this.pedidoRepository = pedidoRepository;
        this.facturaRepository = facturaRepository;
        this.planillaLetraRepository = planillaLetraRepository;
        this.letraRepository = letraRepository;
    }

    /**
     * Método transaccional principal para procesar un Pedido.
     * Lee el pedido, crea la Factura y, si es necesario,
     * genera la Planilla y todas las Letras correspondientes.
     * * @param idPedido El ID del Pedido a procesar.
     * @return La Factura generada.
     */
    @Transactional // ¡MUY IMPORTANTE!
    public Factura procesarPedido(Long idPedido) {
        
        // --- 1. OBTENER DATOS ---
        // Buscamos el pedido o lanzamos un error si no existe
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new RuntimeException("Error: Pedido no encontrado con ID: " + idPedido));

        // Validación: Revisamos si el pedido ya fue facturado
        // (Este es un paso de seguridad para evitar duplicados)
        if (pedido.getEstado() != null && pedido.getEstado().equals("Facturado")) {
            throw new RuntimeException("Error: El pedido " + idPedido + " ya ha sido facturado.");
        }

        LocalDate hoy = LocalDate.now();

        // --- 2. CREAR LA FACTURA (Entidad Hija) ---
        Factura factura = new Factura();
        factura.setPedido(pedido); // Asignamos la relación (clave foránea id_pedido)
        factura.setFechaEmision(hoy);
        
        // Convertimos el Total (Double) del Pedido a BigDecimal para cálculos seguros
        // NOTA: Lo ideal es que 'total' en Pedido y 'montoTotal' en Factura sean BigDecimal.
        BigDecimal montoTotal = new BigDecimal(pedido.getTotal().toString());
        factura.setMontoTotal(montoTotal.doubleValue()); // Guardamos el Double en Factura

        // --- 3. LÓGICA DE NEGOCIO (Condiciones de Pago) ---
        String tipoPago = pedido.getCondicionPagoTipo();
        Integer terminoDias = pedido.getTerminoDias();
        Integer numCuotas = pedido.getNumeroCuotas();

        if ("CONTADO".equals(tipoPago)) {
            factura.setFechaVencimiento(hoy); // Vence hoy mismo
            factura.setEstado("Pendiente de Contado");
        
        } else if ("CREDITO".equals(tipoPago)) {
            factura.setFechaVencimiento(hoy.plusDays(terminoDias)); // Vence en X días
            factura.setEstado("Crédito a " + terminoDias + " días");
        
        } else if ("LETRAS".equals(tipoPago)) {
            // La factura vence en la fecha de la última letra
            factura.setFechaVencimiento(hoy.plusDays(terminoDias));
            factura.setEstado("Canjeado por Letras");
            
            // --- 4. GUARDAR LA FACTURA (1er guardado para obtener ID) ---
            Factura facturaGuardada = facturaRepository.save(factura);

            // --- 4b. GENERAR FOLIO Y ACTUALIZAR (2do guardado) --- // --- CAMBIO ---
            String numeroFactura = String.format("F001-%08d", facturaGuardada.getIdFactura());
            facturaGuardada.setNumeroFactura(numeroFactura);
            facturaRepository.save(facturaGuardada); // Guardamos la actualización
            
            // --- 5. CREAR LA PLANILLA DE LETRAS ---
            PlanillaLetra planilla = new PlanillaLetra();
            planilla.setFactura(facturaGuardada); 
            planilla.setTipoGestion("EN CARTERA"); 
            
            // --- 5b. GUARDAR PLANILLA (1er guardado para obtener ID) --- // --- CAMBIO ---
            PlanillaLetra planillaGuardada = planillaLetraRepository.save(planilla);

            // --- 5c. GENERAR FOLIO Y ACTUALIZAR (2do guardado) --- // --- CAMBIO ---
            String numeroPlanilla = String.format("PL-%05d", planillaGuardada.getIdPlanilla());
            planillaGuardada.setNumeroPlanilla(numeroPlanilla);
            planillaLetraRepository.save(planillaGuardada); // Guardamos la actualización

            
            // --- 6. CÁLCULO Y GENERACIÓN DE LETRAS (Bucle) ---
            BigDecimal montoPorLetra = montoTotal.divide(
                new BigDecimal(numCuotas), 2, RoundingMode.HALF_UP
            );
            int intervaloDias = terminoDias / numCuotas; 
                        
            for (int i = 1; i <= numCuotas; i++) {
                Letra letra = new Letra();
                
                // --- CAMBIO (Usamos la planillaGuardada) ---
                letra.setPlanillaLetra(planillaGuardada); // Asignamos la planilla ya guardada
                
                letra.setNumeroCuota(i + "/" + numCuotas);
                letra.setEstado("Generada");
                letra.setFechaVencimiento(hoy.plusDays(i * intervaloDias));
                
                // Lógica de redondeo (esto sigue igual)
                if (i == numCuotas) {
                    BigDecimal montoAcumulado = montoPorLetra.multiply(new BigDecimal(i - 1));
                    letra.setMonto(montoTotal.subtract(montoAcumulado));
                } else {
                    letra.setMonto(montoPorLetra);
                }
                
                // --- 6b. GUARDAR LETRA (1er guardado, DENTRO del bucle) --- // --- CAMBIO ---
                Letra letraGuardada = letraRepository.save(letra);

                // --- 6c. GENERAR FOLIO Y ACTUALIZAR (2do guardado) --- // --- CAMBIO ---
                String anio = String.valueOf(hoy.getYear());
                String numeroLetra = String.format("%s-%04d", anio, letraGuardada.getIdLetra());
                letraGuardada.setNumeroLetra(numeroLetra);
                letraRepository.save(letraGuardada); // Guardamos la actualización
                
            }
            
        }
        
        // --- 8. FINALIZAR Y ACTUALIZAR PEDIDO ---
        pedido.setEstado("Facturado");
        pedidoRepository.save(pedido); // Actualizamos el estado del pedido

        return factura;
    }
}