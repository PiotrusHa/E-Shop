package piotrusha.e_shop.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Bill {

    private BigDecimal billId;
    private BigDecimal priceSum;
    private Date purchaseDate;
    private Date paymentDate;
    private Date paymentExpirationDate;
    private BigDecimal clientId;
    private BillState billState;
    private Set<BillRecord> billRecords;

    boolean canCancel() {
        return billState == BillState.WAITING_FOR_PAYMENT;
    }

    boolean canPay() {
        return billState == BillState.WAITING_FOR_PAYMENT;
    }

    public void markBillAsCancelled() {
        billState = BillState.CANCELLED;
    }

    public void markBillAsPaid() {
        billState = BillState.PAID;
    }

    BillDto toDto() {
        List<BillRecordDto> recordDtos = billRecords.stream()
                                                    .map(BillRecord::toDto)
                                                    .collect(Collectors.toList());
        return new BillDto().setBillId(billId)
                            .setPriceSum(priceSum)
                            .setPurchaseDate(purchaseDate)
                            .setPaymentDate(paymentDate)
                            .setPaymentExpirationDate(paymentExpirationDate)
                            .setClientId(clientId)
                            .setBillState(billState.toString())
                            .setBillRecords(recordDtos);
    }

}
