package com.mitienda.mt.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitienda.mt.model.TipoCliente;
import com.mitienda.mt.repository.TipoClienteRepository;

@Service
public class TipoClienteService {

    @Autowired
    private TipoClienteRepository tipoClienteRepository;

    // Servicio para llenar el combo
    public List<TipoCliente> listarTiposDeCliente() {
        return tipoClienteRepository.findAll();
    }
}