package piotrusha.e_shop.bill.persistence.jpa;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "bill_records")
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(chain = true)
class BillRecordEntity {

    @Id
    @GeneratedValue(generator = "bill_record_sequencer", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="bill_record_sequencer", sequenceName = "bill_record_seq", allocationSize = 1)
    @Column(nullable = false, unique = true, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal id;

    @Column(nullable = false, columnDefinition = "DECIMAL(19,0)")
    private BigDecimal productId;

    @Column(nullable = false)
    private Integer piecesNumber;

    @Column(nullable = false, scale = 2)
    private BigDecimal piecePrice;

    BillRecordDto toDto() {
        return new BillRecordDto(productId, piecesNumber, piecePrice);
    }

    static BillRecordEntity fromDto(BillRecordDto dto) {
        return new BillRecordEntity().setProductId(dto.getProductId())
                                     .setPiecesNumber(dto.getPiecesNumber())
                                     .setPiecePrice(dto.getPiecePrice());
    }

}
