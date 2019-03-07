package piotrusha.e_shop.core.bill.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class BillDto {

    private BigDecimal billId;
    private BigDecimal priceSum;
    private Date purchaseDate;
    private Date paymentDate;
    private Date paymentExpirationDate;
    private BigDecimal clientId;
    private Set<BillRecordDto> billRecords;

}
