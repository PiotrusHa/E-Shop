package piotrusha.e_shop.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import piotrusha.e_shop.core.exception.UnableToCancelTheBillException;
import piotrusha.e_shop.core.exception.UnableToPayTheBillException;
import piotrusha.e_shop.core.model.Bill;
import piotrusha.e_shop.core.model.BillState;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.builder.BillBuilder;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;
import piotrusha.e_shop.core.repository.BillRepository;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class BillService {

    private BillRepository billRepository;
    private PurchaseProductService purchaseProductService;
    private final long paymentTime;

    @Autowired
    public BillService(BillRepository billRepository, PurchaseProductService purchaseProductService) {
        this.billRepository = billRepository;
        this.purchaseProductService = purchaseProductService;

        paymentTime = 100000L;  // TODO read from system parameters
    }

    public List<Bill> findBillsByClientId(BigDecimal clientId) {
        return billRepository.findByClientId(clientId);
    }

    public List<Bill> findBillsByClientIdAndState(BigDecimal clientId, BillState state) {
        return billRepository.findByClientIdAndState(clientId, state);
    }

    public Bill createBill(List<PurchaseProductDto> purchaseProductDtos, BigDecimal clientId) {
        // TODO validate client
        List<PurchaseProduct> purchaseProducts = purchaseProductService.preparePurchaseProducts(purchaseProductDtos);
        Bill bill = prepareBill(clientId, purchaseProducts);

        return billRepository.save(bill);
    }

    private Bill prepareBill(BigDecimal clientId, List<PurchaseProduct> purchaseProducts) {
        Date currentDate = new Date();
        return new BillBuilder().purchaseDate(currentDate)
                                     .state(BillState.WAITING_FOR_PAYMENT)
                                     .clientId(clientId)
                                     .purchaseProducts(purchaseProducts)
                                     .paymentExpirationDate(new Date(currentDate.getTime() + paymentTime))
                                     .priceSum(calculatePriceSum(purchaseProducts))
                                     .build();
    }

    private BigDecimal calculatePriceSum(List<PurchaseProduct> purchaseProducts) {
        return purchaseProducts.stream()
                               .map(pp -> pp.getPiecePrice().multiply(BigDecimal.valueOf(pp.getPiecesNumber())))
                               .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Bill payBill(BigDecimal billId) {
        Bill bill = billRepository.findById(billId)
                                  .orElseThrow(EntityNotFoundException::new);

        if (bill.getState() != BillState.WAITING_FOR_PAYMENT) {
            throw new UnableToPayTheBillException(bill.getId(), bill.getState());
        }

        bill.setState(BillState.PAID);
        bill.setPaymentDate(new Date());

        return billRepository.save(bill);
    }

    public Bill cancelBill(BigDecimal billId) {
        Bill bill = billRepository.findById(billId)
                                  .orElseThrow(EntityNotFoundException::new);

        if (bill.getState() != BillState.WAITING_FOR_PAYMENT) {
            throw new UnableToCancelTheBillException(bill.getId(), bill.getState());
        }

        purchaseProductService.cancelPurchaseProducts(bill.getPurchaseProducts());
        bill.setState(BillState.CANCELLED);

        return billRepository.save(bill);
    }

    public List<Bill> markBillsWithExpiredPaymentDate() {
        List<Bill> billsToMark = findBillsWithWaitingForPaymentStatusAndExpiredPaymentDate();
        billsToMark.forEach(bill -> {
            purchaseProductService.cancelPurchaseProducts(bill.getPurchaseProducts());
            bill.setState(BillState.PAYMENT_TIME_EXCEEDED);
        });
        return billsToMark;
    }

    private List<Bill> findBillsWithWaitingForPaymentStatusAndExpiredPaymentDate() {
        return billRepository.findByStateAndPaymentExpirationDateBefore(BillState.WAITING_FOR_PAYMENT, new Date());
    }

}
