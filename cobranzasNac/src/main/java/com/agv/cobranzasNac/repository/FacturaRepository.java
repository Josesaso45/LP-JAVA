package com.agv.cobranzasNac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agv.cobranzasNac.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long>{
	List<Factura> findByActivoTrue();

}
