package piotrusha.e_shop.core.product.domain.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class ModifyProductDto {

    private BigDecimal productId;
    private BigDecimal productPrice;
    private String productDescription;
    private Integer productAvailablePiecesNumber;
    private List<String> productCategoriesToAssign;
    private List<String> productCategoriesToUnassign;

}
