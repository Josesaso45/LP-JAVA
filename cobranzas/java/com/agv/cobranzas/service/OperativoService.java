package com.agv.cobranzas.service;

import com.agv.cobranzas.model.LetraPorCobrar;
import com.agv.cobranzas.model.PlanillaLetras;
import com.agv.cobranzas.model.Factura;
import com.agv.cobranzas.repository.LetraRepository;
import com.agv.cobranzas.repository.PlanillaLetrasRepository;
import com.agv.cobranzas.repository.FacturaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OperativoService {

    private final LetraRepository letraRepository;
    private final PlanillaLetrasRepository planillaLetrasRepository;
    private final FacturaRepository facturaRepository;

    public OperativoService(LetraRepository letraRepository, PlanillaLetrasRepository planillaLetrasRepository, FacturaRepository facturaRepository) {
        this.letraRepository = letraRepository;
        this.planillaLetrasRepository = planillaLetrasRepository;
        this.facturaRepository = facturaRepository;
    }

    @Transactional
    public PlanillaLetras generarPlanilla(List<Long> idsDeLetras, Long facturaId) {
        Factura factura = facturaRepository.findById(facturaId)
                .orElseThrow(() -> new IllegalArgumentException("Factura not found with ID: " + facturaId));

        PlanillaLetras planilla = new PlanillaLetras();
        planilla.setCodigoPlanilla("PL-" + System.currentTimeMillis()); // Simple code generation
        planilla.setFechaCreacion(LocalDate.now());
        planilla.setMontoTotal(0.0);
        planilla.setFactura(factura); // Set the associated Factura

        List<LetraPorCobrar> letras = letraRepository.findAllById(idsDeLetras);
        double totalMonto = 0.0;

        for (LetraPorCobrar letra : letras) {
            letra.setPagada(false); // Assuming 'En Cobranza' means not yet paid
            letra.setPlanillaLetras(planilla);
            planilla.getLetras().add(letra); // Explicitly add to the collection
            totalMonto += letra.getMonto();
        }
        planilla.setMontoTotal(totalMonto);
        // planilla.setLetras(letras); // This is now handled by adding each letra in the loop

        planillaLetrasRepository.save(planilla);
        letraRepository.saveAll(letras);

        return planilla;
    }
}
