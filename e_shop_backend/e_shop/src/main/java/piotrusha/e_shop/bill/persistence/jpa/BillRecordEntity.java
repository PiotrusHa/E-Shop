package piotrusha.e_shop.bill.persistence.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.bill.domain.BillRecord;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_records")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(chain = true)
class BillRecordEntity {

    @Id
    private BigDecimal id;
    private BigDecimal productId;
    private Integer piecesNumber;
    private BigDecimal piecePrice;

    BillRecord toDomainBillRecord() {
        return new BillRecord(productId, piecesNumber, piecePrice);
    }

    static BillRecordEntity fromDomainBillRecord(BillRecord billRecord) {
        return new BillRecordEntity().setProductId(billRecord.getProductId())
                                     .setPiecesNumber(billRecord.getPiecesNumber())
                                     .setPiecePrice(billRecord.getPiecePrice());
    }

}
