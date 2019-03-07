package piotrusha.e_shop.core.bill.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BillRecordDto {

    private BigDecimal productId;
    private Integer piecesNumber;

}