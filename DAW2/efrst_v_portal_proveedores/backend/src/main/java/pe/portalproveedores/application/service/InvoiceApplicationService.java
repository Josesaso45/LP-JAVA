package pe.portalproveedores.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.portalproveedores.application.port.in.SubmitSupplierInvoiceUseCase;
import pe.portalproveedores.application.port.in.ValidateThreeWayMatchUseCase;
import pe.portalproveedores.application.port.out.ErpInvoicePort;
import pe.portalproveedores.application.port.out.ErpPurchaseOrderPort;
import pe.portalproveedores.application.port.out.InvoiceSubmissionRepositoryPort;
import pe.portalproveedores.application.port.out.InvoiceXmlParserPort;
import pe.portalproveedores.domain.exception.InvoiceValidationException;
import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.InvoiceSubmissionResult;
import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.SubmitInvoiceCommand;
import pe.portalproveedores.domain.model.SupplierInvoice;
import pe.portalproveedores.domain.model.ThreeWayMatchResult;
import pe.portalproveedores.domain.model.ValidateMatchCommand;
import pe.portalproveedores.domain.service.PeruvianTaxEngine;
import pe.portalproveedores.domain.service.ThreeWayMatchingService;

import java.util.UUID;

@Service
@Transactional
public class InvoiceApplicationService implements SubmitSupplierInvoiceUseCase {

    private final InvoiceXmlParserPort xmlParser;
    private final PeruvianTaxEngine taxEngine;
    private final ThreeWayMatchingService matchingService;
    private final ErpPurchaseOrderPort purchaseOrderPort;
    private final ErpInvoicePort invoicePort;
    private final InvoiceSubmissionRepositoryPort submissionRepository;

    public InvoiceApplicationService(InvoiceXmlParserPort xmlParser,
                                     PeruvianTaxEngine taxEngine,
                                     ThreeWayMatchingService matchingService,
                                     ErpPurchaseOrderPort purchaseOrderPort,
                                     ErpInvoicePort invoicePort,
                                     InvoiceSubmissionRepositoryPort submissionRepository) {
        this.xmlParser = xmlParser;
        this.taxEngine = taxEngine;
        this.matchingService = matchingService;
        this.purchaseOrderPort = purchaseOrderPort;
        this.invoicePort = invoicePort;
        this.submissionRepository = submissionRepository;
    }

    @Override
    public InvoiceSubmissionResult submit(SubmitInvoiceCommand command) {
        if (command.xmlContent() == null || command.xmlContent().length == 0) {
            throw new InvoiceValidationException("El archivo XML es obligatorio");
        }
        if (command.purchaseOrderNumber() == null || command.purchaseOrderNumber().isBlank()) {
            throw new InvoiceValidationException("El número de orden de compra es obligatorio");
        }

        SupplierInvoice invoice = xmlParser.parse(command.xmlContent());
        invoice.setPurchaseOrderNumber(command.purchaseOrderNumber());
        taxEngine.enrich(invoice);

        PurchaseOrderSnapshot po = purchaseOrderPort.findByName(command.purchaseOrderNumber())
                .orElse(null);
        if (po != null) {
            invoice.setErpPurchaseOrderId(po.getId());
        }

        ThreeWayMatchResult matchResult = matchingService.validate(invoice, po);
        ErpInvoiceReference erpReference = null;
        boolean submittedToErp = false;

        if (matchResult.isApproved()) {
            erpReference = invoicePort.createSupplierInvoice(invoice);
            submittedToErp = true;
        }

        InvoiceSubmissionResult result = new InvoiceSubmissionResult(
                UUID.randomUUID().toString(),
                invoice,
                matchResult,
                erpReference,
                submittedToErp
        );
        return submissionRepository.save(result);
    }
}
