package piotrek.e_shop.api.services;

import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.dto.PurchaseProductDto;

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
