package piotrusha.e_shop.core.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.BillRecordDto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "bills")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
class Bill {

    @Id
    private BigDecimal billId;
    private BigDecimal priceSum;
    private Date purchaseDate;
    private Date paymentDate;
    private Date paymentExpirationDate;
    private BigDecimal clientId;
    private BillState billState;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    private Set<BillRecord> billRecords;

    boolean canCancel() {
        return billState == BillState.WAITING_FOR_PAYMENT;
    }

    public void markBillAsCancelled() {
        billState = BillState.CANCELLED;
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
