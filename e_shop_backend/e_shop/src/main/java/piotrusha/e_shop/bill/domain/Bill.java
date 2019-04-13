package piotrusha.e_shop.bill.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Bill {

    private BigDecimal billId;
    private BigDecimal priceSum;
    private LocalDateTime purchaseDate;
    private LocalDate paymentDate;
    private LocalDate paymentExpirationDate;
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
        return BillDto.builder()
                      .billId(billId)
                      .priceSum(priceSum)
                      .purchaseDate(purchaseDate)
                      .paymentDate(paymentDate)
                      .paymentExpirationDate(paymentExpirationDate)
                      .clientId(clientId)
                      .billState(billState.toString())
                      .billRecords(recordDtos)
                      .build();
    }

    static Bill fromDto(BillDto dto) {
        Set<BillRecord> records = dto.getBillRecords()
                                     .stream()
                                     .map(BillRecord::fromDto)
                                     .collect(Collectors.toSet());

        return Bill.builder()
                   .billId(dto.getBillId())
                   .priceSum(dto.getPriceSum())
                   .purchaseDate(dto.getPurchaseDate())
                   .paymentDate(dto.getPaymentDate())
                   .paymentExpirationDate(dto.getPaymentExpirationDate())
                   .clientId(dto.getClientId())
                   .billState(BillState.valueOf(dto.getBillState()))
                   .billRecords(records)
                   .build();
    }

}
