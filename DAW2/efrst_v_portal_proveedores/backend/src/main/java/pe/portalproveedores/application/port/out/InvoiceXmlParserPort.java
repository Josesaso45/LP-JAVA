package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.SupplierInvoice;

public interface InvoiceXmlParserPort {

    SupplierInvoice parse(byte[] xmlContent);
}
