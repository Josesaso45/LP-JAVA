package com.agv.cobranzas.repository;

import com.agv.cobranzas.model.PlanillaLetras;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanillaLetrasRepository extends JpaRepository<PlanillaLetras, Long> {
}
