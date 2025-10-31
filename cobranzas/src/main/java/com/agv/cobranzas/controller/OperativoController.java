package com.agv.cobranzas.controller;

import com.agv.cobranzas.model.LetraPorCobrar;
import com.agv.cobranzas.service.LetraService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
public class OperativoController {

    private final LetraService letraService;

    public OperativoController(LetraService letraService) {
        this.letraService = letraService;
    }

    @GetMapping("/operativo")
    public String operativoView(Model model) {
        // You might add some initial data or setup for the view here
        return "vista_operativa"; // Corresponds to /resources/templates/vista_operativa.html
    }

    @GetMapping("/operativo/letras")
    @ResponseBody
    public List<LetraPorCobrar> getLetras(
            @RequestParam(required = false) Boolean pagada,
            @RequestParam(required = false) String rucCliente,
            @RequestParam(required = false) LocalDate fechaInicio,
            @RequestParam(required = false) LocalDate fechaFin) {
        return letraService.findLetrasByCriteria(pagada, rucCliente, fechaInicio, fechaFin);
    }
}
