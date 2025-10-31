package com.agv.cobranzas.service;

import com.agv.cobranzas.dto.AgingData;
import com.agv.cobranzas.dto.KpiDto;
import com.agv.cobranzas.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final FacturaRepository facturaRepository;

    public DashboardService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public KpiDto getKpisEjecutivos() {
        // Placeholder for KPI calculation
        // In a real application, this would involve more complex queries
        // and aggregations from various repositories.
        Double totalFacturado = facturaRepository.findAll().stream()
                .mapToDouble(f -> f.getMontoTotal() != null ? f.getMontoTotal() : 0.0)
                .sum();
        Long facturasPendientes = facturaRepository.countByPagada(false);
        Double montoPendiente = facturaRepository.findByPagada(false).stream()
                .mapToDouble(f -> f.getMontoTotal() != null ? f.getMontoTotal() : 0.0)
                .sum();
        // Example TPC calculation (Total Paid / Total Billed)
        Double tpc = (totalFacturado > 0) ? (totalFacturado - montoPendiente) / totalFacturado : 0.0;

        return new KpiDto(totalFacturado, facturasPendientes, montoPendiente, tpc);
    }

    public List<AgingData> getAgingData() {
        List<Object[]> rawData = facturaRepository.getPortfolioAgingData();
        return rawData.stream()
                .map(row -> new AgingData((String) row[0], (Double) row[1]))
                .collect(Collectors.toList());
    }
}
