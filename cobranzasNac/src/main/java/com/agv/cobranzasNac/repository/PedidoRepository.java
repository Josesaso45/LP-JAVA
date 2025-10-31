package com.agv.cobranzasNac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agv.cobranzasNac.model.Pedido;

/**
 * Repositorio para la entidad Pedido.
 * JpaRepository nos provee todos los métodos CRUD básicos.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	List<Pedido> findByActivoTrue();
    


}