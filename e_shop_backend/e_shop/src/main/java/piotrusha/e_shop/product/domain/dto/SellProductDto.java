package piotrusha.e_shop.product.domain.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class SellProductDto extends AbstractProductActionDto {

    private Integer piecesNumber;

    public SellProductDto(BigDecimal productId, Integer piecesNumber) {
        super(productId);
        this.piecesNumber = piecesNumber;
    }

}
