package com.mitienda.mt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mitienda.mt.model.Cliente;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    List<Cliente> findByTipoClienteIdTipoCliente(Integer idTipo);
}