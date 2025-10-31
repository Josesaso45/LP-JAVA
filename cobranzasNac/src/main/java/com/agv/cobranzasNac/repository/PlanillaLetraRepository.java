package com.agv.cobranzasNac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agv.cobranzasNac.model.PlanillaLetra;

public interface PlanillaLetraRepository extends JpaRepository<PlanillaLetra, Long>{
	List<PlanillaLetra> findByActivoTrue();

}
