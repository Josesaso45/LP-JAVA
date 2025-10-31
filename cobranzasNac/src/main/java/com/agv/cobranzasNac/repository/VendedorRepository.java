package com.agv.cobranzasNac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agv.cobranzasNac.model.Vendedor;

public interface VendedorRepository extends JpaRepository<Vendedor, Long>{
	
	List<Vendedor> findByActivoTrue();
}
