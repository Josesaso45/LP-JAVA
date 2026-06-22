package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.ErpInvoiceReference;
import pe.portalproveedores.domain.model.ErpInvoiceStatus;
import pe.portalproveedores.domain.model.SupplierInvoice;

import java.util.Optional;

public interface ErpInvoicePort {

    ErpInvoiceReference createSupplierInvoice(SupplierInvoice invoice);

    Optional<ErpInvoiceStatus> findByReference(String serialNumber, String invoiceNumber);
}
