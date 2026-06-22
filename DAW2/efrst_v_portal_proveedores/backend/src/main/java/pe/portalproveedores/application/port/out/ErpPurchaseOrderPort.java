package pe.portalproveedores.application.port.out;

import pe.portalproveedores.domain.model.PurchaseOrderSnapshot;
import pe.portalproveedores.domain.model.ReceivedQuantityLine;

import java.util.List;
import java.util.Optional;

public interface ErpPurchaseOrderPort {

    Optional<PurchaseOrderSnapshot> findByName(String poNumber);

    List<ReceivedQuantityLine> getReceivedQuantities(int purchaseOrderId);
}
