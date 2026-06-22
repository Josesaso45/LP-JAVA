package pe.portalproveedores.infrastructure.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pe.portalproveedores.application.port.in.SubmitSupplierInvoiceUseCase;
import pe.portalproveedores.application.port.in.ValidateThreeWayMatchUseCase;
import pe.portalproveedores.domain.model.SubmitInvoiceCommand;
import pe.portalproveedores.domain.model.ValidateMatchCommand;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.InvoiceUploadResponse;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.MatchStatusResponse;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final SubmitSupplierInvoiceUseCase submitUseCase;
    private final ValidateThreeWayMatchUseCase validateUseCase;

    public InvoiceController(SubmitSupplierInvoiceUseCase submitUseCase,
                             ValidateThreeWayMatchUseCase validateUseCase) {
        this.submitUseCase = submitUseCase;
        this.validateUseCase = validateUseCase;
    }

    @PostMapping("/upload")
    public ResponseEntity<InvoiceUploadResponse> upload(
            @RequestParam("xml") MultipartFile xml,
            @RequestParam("purchaseOrderNumber") String purchaseOrderNumber,
            @RequestParam(value = "submittedBy", required = false) String submittedBy) throws IOException {

        SubmitInvoiceCommand command = new SubmitInvoiceCommand(
                xml.getBytes(),
                purchaseOrderNumber,
                submittedBy
        );
        return ResponseEntity.ok(InvoiceUploadResponse.from(submitUseCase.submit(command)));
    }

    @GetMapping("/{id}/match-status")
    public ResponseEntity<MatchStatusResponse> matchStatus(@PathVariable String id) {
        var result = validateUseCase.validate(new ValidateMatchCommand(id, null, null));
        return ResponseEntity.ok(MatchStatusResponse.from(result));
    }
}
