package piotrusha.e_shop.product.domain.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CancelProductBookingDto extends AbstractProductActionDto {

    private Integer piecesNumber;

    public CancelProductBookingDto(BigDecimal productId, Integer piecesNumber) {
        super(productId);
        this.piecesNumber = piecesNumber;
    }

}
