package piotrusha.e_shop.bill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BillActionDto {

    private BigDecimal billId;

}
