package piotrusha.e_shop.core.product.domain.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BookProductDto extends AbstractProductActionDto {

    private Integer piecesNumber;

    public BookProductDto(BigDecimal productId, Integer piecesNumber) {
        super(productId);
        this.piecesNumber = piecesNumber;
    }

}
