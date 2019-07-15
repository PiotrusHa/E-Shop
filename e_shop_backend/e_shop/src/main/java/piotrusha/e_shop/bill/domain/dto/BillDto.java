package piotrusha.e_shop.bill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

    private BigDecimal billId;
    private BigDecimal priceSum;
    private LocalDateTime purchaseDate;
    private LocalDate paymentDate;
    private LocalDate paymentExpirationDate;
    private BigDecimal clientId;
    private String billState;
    private List<BillRecordDto> billRecords;

}
