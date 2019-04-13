package piotrusha.e_shop.bill.persistence.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    private LocalDateTime purchaseDate;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(nullable = false)
    private LocalDate paymentExpirationDate;

    @Column(nullable = false, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal clientId;

    @Column(nullable = false)
    private String billState;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    private Set<BillRecordEntity> billRecords;

    BillDto toDto() {
        List<BillRecordDto> dtos = billRecords.stream()
                                              .map(BillRecordEntity::toDto)
                                              .collect(Collectors.toList());
        return BillDto.builder()
                      .billId(billId)
                      .priceSum(priceSum)
                      .purchaseDate(purchaseDate)
                      .paymentDate(paymentDate)
                      .paymentExpirationDate(paymentExpirationDate)
                      .clientId(clientId)
                      .billState(billState)
                      .billRecords(dtos)
                      .build();
    }

    static BillEntity fromDto(BillDto bill) {
        Set<BillRecordEntity> records = bill.getBillRecords()
                                            .stream()
                                            .map(BillRecordEntity::fromDto)
                                            .collect(Collectors.toSet());
        return new BillEntity().setBillId(bill.getBillId())
                               .setPriceSum(bill.getPriceSum())
                               .setPurchaseDate(bill.getPurchaseDate())
                               .setPaymentDate(bill.getPaymentDate())
                               .setPaymentExpirationDate(bill.getPaymentExpirationDate())
                               .setClientId(bill.getClientId())
                               .setBillState(bill.getBillState())
                               .setBillRecords(records);
    }

}
