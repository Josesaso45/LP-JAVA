package com.mitienda.mt.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mitienda.mt.model.Cliente;
import com.mitienda.mt.repository.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void registrarCliente(Cliente cliente) {
        clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientesPorTipo(Integer idTipo) {
        return clienteRepository.findByTipoClienteIdTipoCliente(idTipo);
    }
}