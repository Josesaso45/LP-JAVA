package pe.portalproveedores.infrastructure.adapter.in.web;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.portalproveedores.application.port.in.GeneratePaymentBatchUseCase;
import pe.portalproveedores.application.service.PaymentBatchApplicationService;
import pe.portalproveedores.domain.model.GenerateBatchCommand;
import pe.portalproveedores.domain.model.GeneratedFile;
import pe.portalproveedores.domain.model.PaymentBatchCriteria;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.GenerateBatchRequest;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.OpenVendorBillResponse;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.PaymentBatchResponse;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/treasury")
public class TreasuryController {

    private final GeneratePaymentBatchUseCase generateBatchUseCase;
    private final PaymentBatchApplicationService paymentBatchService;

    public TreasuryController(GeneratePaymentBatchUseCase generateBatchUseCase,
                              PaymentBatchApplicationService paymentBatchService) {
        this.generateBatchUseCase = generateBatchUseCase;
        this.paymentBatchService = paymentBatchService;
    }

    @GetMapping("/open-bills")
    public ResponseEntity<List<OpenVendorBillResponse>> openBills(
            @RequestParam(required = false) String currencyCode,
            @RequestParam(required = false) LocalDate dueDateFrom,
            @RequestParam(required = false) LocalDate dueDateTo) {

        PaymentBatchCriteria criteria = new PaymentBatchCriteria(currencyCode, dueDateFrom, dueDateTo, null);
        List<OpenVendorBillResponse> response = paymentBatchService.listOpenBills(criteria).stream()
                .map(OpenVendorBillResponse::from)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/batches")
    public ResponseEntity<PaymentBatchResponse> createBatch(@RequestBody GenerateBatchRequest request) {
        GenerateBatchCommand command = new GenerateBatchCommand(
                request.paymentDate(),
                request.currencyCode(),
                request.invoiceIds()
        );
        return ResponseEntity.ok(PaymentBatchResponse.from(generateBatchUseCase.generate(command)));
    }

    @GetMapping("/batches/{id}/download")
    public ResponseEntity<byte[]> downloadBatch(@PathVariable String id) {
        GeneratedFile file = paymentBatchService.downloadBatch(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.fileName() + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .body(file.content());
    }
}
