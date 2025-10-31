package com.agv.cobranzas.service;

import com.agv.cobranzas.model.LetraPorCobrar;
import com.agv.cobranzas.repository.LetraRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LetraService {

    private final LetraRepository letraRepository;

    public LetraService(LetraRepository letraRepository) {
        this.letraRepository = letraRepository;
    }

    public List<LetraPorCobrar> findAll() {
        return letraRepository.findAll();
    }

    public List<LetraPorCobrar> findLetrasByCriteria(Boolean pagada, String rucCliente, LocalDate fechaInicio, LocalDate fechaFin) {
        return letraRepository.findByCriteria(pagada, rucCliente, fechaInicio, fechaFin);
    }

    public LetraPorCobrar save(LetraPorCobrar letra) {
        return letraRepository.save(letra);
    }

    public void deleteById(Long id) {
        letraRepository.deleteById(id);
    }
}
