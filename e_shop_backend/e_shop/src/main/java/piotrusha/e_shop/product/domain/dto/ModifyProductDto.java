package piotrusha.e_shop.product.domain.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ModifyProductDto extends AbstractProductActionDto {

    private BigDecimal productPrice;
    private String productDescription;
    private Integer productAvailablePiecesNumber;
    private List<String> productCategoriesToAssign;
    private List<String> productCategoriesToUnassign;

    public ModifyProductDto(BigDecimal productId) {
        super(productId);
    }

}
