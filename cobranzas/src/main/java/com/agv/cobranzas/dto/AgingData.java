package com.agv.cobranzas.dto;

public class AgingData {
    private String ageRange;
    private Double totalDebt;

    // Constructors
    public AgingData() {
    }

    public AgingData(String ageRange, Double totalDebt) {
        this.ageRange = ageRange;
        this.totalDebt = totalDebt;
    }

    // Getters and Setters
    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public Double getTotalDebt() {
        return totalDebt;
    }

    public void setTotalDebt(Double totalDebt) {
        this.totalDebt = totalDebt;
    }
}
