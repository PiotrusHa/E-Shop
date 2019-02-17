package piotrusha.e_shop.core.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class CreateProductDto {

    private String productName;
    private BigDecimal price;
    private Integer availablePiecesNumber;
    private String description;
    private List<String> categories;

}
