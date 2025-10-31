package com.agv.cobranzas.controller;

import com.agv.cobranzas.dto.AgingData;
import com.agv.cobranzas.dto.KpiDto;
import com.agv.cobranzas.service.DashboardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final ObjectMapper objectMapper; // For converting to JSON

    public DashboardController(DashboardService dashboardService, ObjectMapper objectMapper) {
        this.dashboardService = dashboardService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/dashboard")
    public String getDashboard(Model model) throws JsonProcessingException {
        // 1. Call the service
        KpiDto kpis = dashboardService.getKpisEjecutivos();
        List<AgingData> aging = dashboardService.getAgingData();

        // 2. Add to the model
        model.addAttribute("kpis", kpis);
        model.addAttribute("agingJson", objectMapper.writeValueAsString(aging)); // Convert to JSON for Chart.js

        // 3. Return the name of the HTML (Thymeleaf)
        return "vista_ejecutiva"; // Corresponds to /resources/templates/vista_ejecutiva.html
    }
}
