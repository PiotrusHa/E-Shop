package piotrusha.e_shop.bill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillDto {

    private BigDecimal billId;
    private BigDecimal priceSum;
    private Date purchaseDate;
    private Date paymentDate;
    private Date paymentExpirationDate;
    private BigDecimal clientId;
    private String billState;
    private List<BillRecordDto> billRecords;

}
