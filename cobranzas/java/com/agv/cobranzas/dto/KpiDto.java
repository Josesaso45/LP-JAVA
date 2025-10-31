package com.agv.cobranzas.dto;

public class KpiDto {
    private Double totalFacturado;
    private Long facturasPendientes;
    private Double montoPendiente;
    private Double tpc; // Placeholder for a specific KPI like 'Tasa de Pago de Clientes'

    // Constructors
    public KpiDto() {
    }

    public KpiDto(Double totalFacturado, Long facturasPendientes, Double montoPendiente, Double tpc) {
        this.totalFacturado = totalFacturado;
        this.facturasPendientes = facturasPendientes;
        this.montoPendiente = montoPendiente;
        this.tpc = tpc;
    }

    // Getters and Setters
    public Double getTotalFacturado() {
        return totalFacturado;
    }

    public void setTotalFacturado(Double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    public Long getFacturasPendientes() {
        return facturasPendientes;
    }

    public void setFacturasPendientes(Long facturasPendientes) {
        this.facturasPendientes = facturasPendientes;
    }

    public Double getMontoPendiente() {
        return montoPendiente;
    }

    public void setMontoPendiente(Double montoPendiente) {
        this.montoPendiente = montoPendiente;
    }

    public Double getTpc() {
        return tpc;
    }

    public void setTpc(Double tpc) {
        this.tpc = tpc;
    }
}
