package pe.portalproveedores.infrastructure.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.portalproveedores.application.port.in.ValidateThreeWayMatchUseCase;
import pe.portalproveedores.domain.model.ValidateMatchCommand;
import pe.portalproveedores.application.port.out.InvoiceXmlParserPort;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;
import pe.portalproveedores.infrastructure.adapter.in.web.dto.ValidateMatchRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/matching")
public class MatchingController {

    private final ValidateThreeWayMatchUseCase validateUseCase;
    private final InvoiceXmlParserPort xmlParser;

    public MatchingController(ValidateThreeWayMatchUseCase validateUseCase, InvoiceXmlParserPort xmlParser) {
        this.validateUseCase = validateUseCase;
        this.xmlParser = xmlParser;
    }

    @PostMapping("/validate")
    public ResponseEntity<ThreeWayMatchResult> validate(@Valid @RequestBody ValidateMatchRequest request) {
        SupplierInvoice invoice = xmlParser.parse(request.getXmlContent().getBytes());
        ThreeWayMatchResult result = validateUseCase.validate(new ValidateMatchCommand(
                null,
                invoice,
                request.getPurchaseOrderNumber()
        ));
        return ResponseEntity.ok(result);
    }
}
