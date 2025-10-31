package com.agv.cobranzas.repository;

import com.agv.cobranzas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Long countByPagada(Boolean pagada);
    List<Factura> findByPagada(Boolean pagada);

    @Query(value = "SELECT " +
                   "CASE " +
                   "    WHEN DATEDIFF(CURRENT_DATE(), f.fechaEmision) <= 30 THEN '0-30 days' " +
                   "    WHEN DATEDIFF(CURRENT_DATE(), f.fechaEmision) > 30 AND DATEDIFF(CURRENT_DATE(), f.fechaEmision) <= 60 THEN '31-60 days' " +
                   "    WHEN DATEDIFF(CURRENT_DATE(), f.fechaEmision) > 60 AND DATEDIFF(CURRENT_DATE(), f.fechaEmision) <= 90 THEN '61-90 days' " +
                   "    ELSE '>90 days' " +
                   "END AS age_range, " +
                   "SUM(f.montoTotal) AS total_debt " +
                   "FROM Factura f " +
                   "WHERE f.pagada = FALSE " +
                   "GROUP BY age_range")
    List<Object[]> getPortfolioAgingData();
}