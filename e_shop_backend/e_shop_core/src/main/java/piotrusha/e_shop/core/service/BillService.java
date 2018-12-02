package piotrusha.e_shop.core.service;

import piotrusha.e_shop.core.model.Bill;
import piotrusha.e_shop.core.model.BillState;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {

    List<Bill> findBillsByClientId(BigDecimal clientId);

    List<Bill> findBillsByClientIdAndState(BigDecimal clientId, BillState state);

    Bill createBill(List<PurchaseProductDto> purchaseProductDtos, BigDecimal clientId);

    Bill payBill(BigDecimal billId);

    Bill cancelBill(BigDecimal billId);

    List<Bill> markBillsWithExpiredPaymentDate();

}
