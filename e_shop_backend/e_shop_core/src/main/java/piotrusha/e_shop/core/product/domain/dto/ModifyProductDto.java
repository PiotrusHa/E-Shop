package piotrusha.e_shop.core.product.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class ModifyProductDto {

    private BigDecimal productId;
    private BigDecimal productPrice;
    private String productDescription;
    private Integer productAvailablePiecesNumber;

}
