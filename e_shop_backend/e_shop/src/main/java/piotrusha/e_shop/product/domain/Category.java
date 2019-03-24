package piotrusha.e_shop.product.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Category {

    private String name;

    ProductCategoryDto toDto() {
        return new ProductCategoryDto(name);
    }

}
