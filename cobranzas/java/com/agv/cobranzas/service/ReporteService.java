package com.agv.cobranzas.service;

import com.agv.cobranzas.dto.AgingData;
import com.agv.cobranzas.repository.FacturaRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReporteService {

    private final FacturaRepository facturaRepository;

    public ReporteService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public byte[] generateAgingReportPdf() throws FileNotFoundException, JRException {
        // 1. Fetch data for the report
        List<Object[]> rawData = facturaRepository.getPortfolioAgingData();
        List<AgingData> agingDataList = rawData.stream()
                .map(row -> new AgingData((String) row[0], (Double) row[1]))
                .collect(Collectors.toList());

        // 2. Load and compile the JRXML report file
        File file = ResourceUtils.getFile("classpath:reports/AgingReport.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // 3. Prepare data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(agingDataList);

        // 4. Prepare parameters (if any)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("ReportTitle", "Reporte de Aging de Cartera");

        // 5. Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // 6. Export to PDF
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
