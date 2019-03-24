package piotrusha.e_shop.product.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Category {

    private String name;

    ProductCategoryDto toDto() {
        return new ProductCategoryDto(name);
    }

}
