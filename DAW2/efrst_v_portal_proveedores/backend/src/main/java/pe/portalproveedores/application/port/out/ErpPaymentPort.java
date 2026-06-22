package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.OpenVendorBill;
import pe.portalproveedores.domain.model.PaymentBatchCriteria;

import java.util.List;

public interface ErpPaymentPort {

    List<OpenVendorBill> findOpenVendorBills(PaymentBatchCriteria criteria);
}
