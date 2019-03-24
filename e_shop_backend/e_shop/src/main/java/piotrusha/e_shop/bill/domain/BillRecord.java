package piotrusha.e_shop.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BillRecord {

    private BigDecimal productId;
    private Integer piecesNumber;
    private BigDecimal piecePrice;

    BigDecimal getPriceSum() {
        return piecePrice.multiply(BigDecimal.valueOf(piecesNumber));
    }

    BillRecordDto toDto() {
        return new BillRecordDto(productId, piecesNumber, piecePrice, getPriceSum());
    }

}
