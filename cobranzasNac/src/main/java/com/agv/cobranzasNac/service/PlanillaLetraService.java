package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.repository.PlanillaLetraRepository;
import com.agv.cobranzasNac.model.PlanillaLetra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanillaLetraService {

    @Autowired
    private PlanillaLetraRepository planillaLetraRepository;

    /**
     * Obtiene todas las planillas ACTIVAS.
     */
    public List<PlanillaLetra> listarTodos() {
        return planillaLetraRepository.findByActivoTrue();
    }

    /**
     * Busca una planilla por id.
     */
    public Optional<PlanillaLetra> buscarPorId(Long id) {
        return planillaLetraRepository.findById(id);
    }

    /**
     * Guarda o actualiza una planilla.
     */
    public PlanillaLetra guardar(PlanillaLetra planilla) {
        return planillaLetraRepository.save(planilla);
    }

    /**
     * Elimina una planilla (Baja Lógica).
     */
    public void eliminar(Long id) {
        Optional<PlanillaLetra> planillaOpt = planillaLetraRepository.findById(id);
        
        if (planillaOpt.isPresent()) {
            PlanillaLetra planilla = planillaOpt.get();
            planilla.setActivo(false);
            planillaLetraRepository.save(planilla);
        }
    }
}