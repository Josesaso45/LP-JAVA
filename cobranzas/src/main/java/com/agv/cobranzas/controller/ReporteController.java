package com.agv.cobranzas.controller;

import com.agv.cobranzas.service.ReporteService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileNotFoundException;

@Controller
public class ReporteController {

    private final ReporteService reporteService;

    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @GetMapping("/reportes/aging-pdf")
    public ResponseEntity<byte[]> generateAgingReportPdf() {
        try {
            byte[] pdfBytes = reporteService.generateAgingReportPdf();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "aging_report.pdf");
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (FileNotFoundException e) {
            // Log the error and return a 404 or 500 error
            e.printStackTrace(); // For debugging
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (JRException e) {
            // Log the error and return a 500 error
            e.printStackTrace(); // For debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
