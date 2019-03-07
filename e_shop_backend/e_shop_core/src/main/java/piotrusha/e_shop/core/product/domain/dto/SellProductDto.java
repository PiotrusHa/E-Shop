package piotrusha.e_shop.core.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class SellProductDto {

    private BigDecimal productId;
    private Integer piecesNumber;

}
