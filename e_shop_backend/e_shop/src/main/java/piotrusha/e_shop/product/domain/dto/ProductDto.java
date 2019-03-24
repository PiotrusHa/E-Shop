package piotrusha.e_shop.product.domain.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Accessors(chain = true)
public class ProductDto {

    private BigDecimal productId;
    private String name;
    private BigDecimal price;
    private Integer availablePiecesNumber;
    private Integer bookedPiecesNumber;
    private Integer soldPiecesNumber;
    private String description;
    private List<String> categories;

}
