package com.agv.cobranzasNac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agv.cobranzasNac.model.Letra;

public interface LetraRepository extends JpaRepository<Letra, Long>{
	List<Letra> findByActivoTrue();

}
