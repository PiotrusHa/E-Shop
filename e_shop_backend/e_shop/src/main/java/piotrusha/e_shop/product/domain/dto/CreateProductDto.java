package piotrusha.e_shop.product.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateProductDto {

    private String productName;
    private BigDecimal price;
    private Integer availablePiecesNumber;
    private String description;
    private List<String> categories;

}
