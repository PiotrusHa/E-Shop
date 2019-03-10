package piotrusha.e_shop.core.bill.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import piotrusha.e_shop.core.bill.domain.dto.BillRecordDto;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_records")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class BillRecord {

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
