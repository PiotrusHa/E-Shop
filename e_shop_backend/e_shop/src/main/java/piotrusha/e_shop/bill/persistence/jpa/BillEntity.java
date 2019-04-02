package piotrusha.e_shop.bill.persistence.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.bill.domain.Bill;
import piotrusha.e_shop.bill.domain.BillRecord;
import piotrusha.e_shop.bill.domain.BillState;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "bills")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(chain = true)
class BillEntity {

    @Id
    @Column(nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal billId;

    @Column(nullable = false, scale = 2)
    private BigDecimal priceSum;

    @Column(nullable = false)
    private Date purchaseDate;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private Date paymentExpirationDate;

    @Column(nullable = false, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal clientId;

    @Column(nullable = false)
    private BillState billState;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    private Set<BillRecordEntity> billRecords;

    Bill toDomainBill() {
        Set<BillRecord> billRecordEntities = billRecords.stream()
                                                        .map(BillRecordEntity::toDomainBillRecord)
                                                        .collect(Collectors.toSet());
        return Bill.builder()
                   .billId(billId)
                   .priceSum(priceSum)
                   .purchaseDate(purchaseDate)
                   .paymentDate(paymentDate)
                   .paymentExpirationDate(paymentExpirationDate)
                   .clientId(clientId)
                   .billState(billState)
                   .billRecords(billRecordEntities)
                   .build();
    }

    static BillEntity fromDomainBill(Bill bill, boolean includeBillRecords) {
        Set<BillRecordEntity> recordEntities = includeBillRecords ? bill.getBillRecords()
                                                                        .stream()
                                                                        .map(BillRecordEntity::fromDomainBillRecord)
                                                                        .collect(Collectors.toSet()) : null;
        return new BillEntity().setBillId(bill.getBillId())
                               .setPriceSum(bill.getPriceSum())
                               .setPurchaseDate(bill.getPurchaseDate())
                               .setPaymentDate(bill.getPaymentDate())
                               .setPaymentExpirationDate(bill.getPaymentExpirationDate())
                               .setClientId(bill.getClientId())
                               .setBillState(bill.getBillState())
                               .setBillRecords(recordEntities);
    }

}
