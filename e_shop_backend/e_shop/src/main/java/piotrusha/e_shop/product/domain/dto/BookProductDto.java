package piotrusha.e_shop.product.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class BookProductDto extends AbstractProductActionDto {

    private Integer piecesNumber;

    public BookProductDto(BigDecimal productId, Integer piecesNumber) {
        super(productId);
        this.piecesNumber = piecesNumber;
    }

}
