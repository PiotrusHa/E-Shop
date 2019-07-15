package piotrusha.e_shop.product.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class SellProductDto extends AbstractProductActionDto {

    private Integer piecesNumber;

    public SellProductDto(BigDecimal productId, Integer piecesNumber) {
        super(productId);
        this.piecesNumber = piecesNumber;
    }

}
