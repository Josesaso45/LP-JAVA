package com.mitienda.mt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mitienda.mt.model.TipoCliente;

public interface TipoClienteRepository extends JpaRepository<TipoCliente, Integer> {
}