package com.agv.cobranzasNac.service;

import com.agv.cobranzasNac.model.Vendedor;
import com.agv.cobranzasNac.repository.VendedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendedorService {

    @Autowired
    private VendedorRepository vendedorRepository;

    /**
     * Obtiene todos los vendedores ACTIVOS.
     */
    public List<Vendedor> listarTodos() {
        return vendedorRepository.findByActivoTrue();
    }

    public Optional<Vendedor> buscarPorId(Long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor guardar(Vendedor vendedor) {
        return vendedorRepository.save(vendedor);
    }

    /**
     * Elimina un vendedor (Baja Lógica).
     */
    public void eliminar(Long id) {
        Optional<Vendedor> vendedorOpt = vendedorRepository.findById(id);
        
        if (vendedorOpt.isPresent()) {
            Vendedor vendedor = vendedorOpt.get();
            vendedor.setActivo(false);
            vendedorRepository.save(vendedor);
        }
    }
}