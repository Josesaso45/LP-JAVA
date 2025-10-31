package com.agv.cobranzas.repository;

import com.agv.cobranzas.model.LetraPorCobrar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LetraRepository extends JpaRepository<LetraPorCobrar, Long> {

    @Query("SELECT l FROM LetraPorCobrar l WHERE " +
           "(:pagada IS NULL OR l.pagada = :pagada) AND " +
           "(:rucCliente IS NULL OR l.factura.cliente.ruc = :rucCliente) AND " +
           "(:fechaInicio IS NULL OR l.fechaVencimiento >= :fechaInicio) AND " +
           "(:fechaFin IS NULL OR l.fechaVencimiento <= :fechaFin)")
    List<LetraPorCobrar> findByCriteria(
            @Param("pagada") Boolean pagada,
            @Param("rucCliente") String rucCliente,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin
    );
}