package pe.portalproveedores.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.portalproveedores.application.port.in.GeneratePaymentBatchUseCase;
import pe.portalproveedores.application.port.out.ErpPaymentPort;
import pe.portalproveedores.application.port.out.TreasuryFileGeneratorPort;
import pe.portalproveedores.domain.exception.DomainException;
import pe.portalproveedores.domain.model.BankFormat;
import pe.portalproveedores.domain.model.GenerateBatchCommand;
import pe.portalproveedores.domain.model.GeneratedFile;
import pe.portalproveedores.domain.model.OpenVendorBill;
import pe.portalproveedores.domain.model.PaymentBatch;
import pe.portalproveedores.domain.model.PaymentBatchCriteria;
import pe.portalproveedores.domain.model.PaymentBatchFile;
import pe.portalproveedores.domain.model.PaymentBatchLine;
import pe.portalproveedores.infrastructure.adapter.out.persistence.PaymentBatchEntity;
import pe.portalproveedores.infrastructure.adapter.out.persistence.PaymentBatchJpaRepository;
import pe.portalproveedores.infrastructure.config.TreasuryProperties;

import java.util.List;

@Service
@Transactional
public class PaymentBatchApplicationService implements GeneratePaymentBatchUseCase {

    private final ErpPaymentPort paymentPort;
    private final TreasuryFileGeneratorPort fileGenerator;
    private final TreasuryProperties treasuryProperties;
    private final PaymentBatchJpaRepository batchRepository;

    public PaymentBatchApplicationService(ErpPaymentPort paymentPort,
                                          TreasuryFileGeneratorPort fileGenerator,
                                          TreasuryProperties treasuryProperties,
                                          PaymentBatchJpaRepository batchRepository) {
        this.paymentPort = paymentPort;
        this.fileGenerator = fileGenerator;
        this.treasuryProperties = treasuryProperties;
        this.batchRepository = batchRepository;
    }

    @Override
    public PaymentBatchFile generate(GenerateBatchCommand command) {
        PaymentBatchCriteria criteria = new PaymentBatchCriteria(
                command.currencyCode(),
                null,
                null,
                command.invoiceIds()
        );

        List<OpenVendorBill> bills = paymentPort.findOpenVendorBills(criteria);
        if (bills.isEmpty()) {
            throw new DomainException("No se encontraron facturas abiertas para el lote");
        }

        PaymentBatch batch = new PaymentBatch();
        batch.setPaymentDate(command.paymentDate());
        batch.setCurrencyCode(command.currencyCode() != null ? command.currencyCode() : treasuryProperties.getCurrencyCode());
        batch.setOriginAccount(treasuryProperties.getOriginAccount());

        for (OpenVendorBill bill : bills) {
            batch.getLines().add(new PaymentBatchLine(
                    bill.erpInvoiceId(),
                    bill.invoiceReference(),
                    bill.supplierRuc(),
                    bill.supplierName(),
                    bill.bankAccount(),
                    bill.cci(),
                    bill.amountDue(),
                    bill.invoiceReference()
            ));
        }

        GeneratedFile file = fileGenerator.generate(batch, BankFormat.BCP_TELECREDITO);
        persistBatch(batch, file);
        return new PaymentBatchFile(batch, file);
    }

    @Transactional(readOnly = true)
    public GeneratedFile downloadBatch(String batchId) {
        PaymentBatchEntity entity = batchRepository.findById(batchId)
                .orElseThrow(() -> new DomainException("Lote no encontrado: " + batchId));
        return new GeneratedFile(entity.getFileName(), entity.getFileContent(), "text/plain");
    }

    @Transactional(readOnly = true)
    public List<OpenVendorBill> listOpenBills(PaymentBatchCriteria criteria) {
        return paymentPort.findOpenVendorBills(criteria);
    }

    private void persistBatch(PaymentBatch batch, GeneratedFile file) {
        PaymentBatchEntity entity = new PaymentBatchEntity();
        entity.setId(batch.getId());
        entity.setPaymentDate(batch.getPaymentDate());
        entity.setCurrencyCode(batch.getCurrencyCode());
        entity.setOriginAccount(batch.getOriginAccount());
        entity.setFileName(file.fileName());
        entity.setFileContent(file.content());
        batchRepository.save(entity);
    }
}
