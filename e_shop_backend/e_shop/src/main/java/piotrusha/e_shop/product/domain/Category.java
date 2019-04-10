package piotrusha.e_shop.product.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
class Category {

    private String name;

    ProductCategoryDto toDto() {
        return new ProductCategoryDto(name);
    }

    static Category fromDto(ProductCategoryDto dto) {
        return new Category(dto.getName());
    }

}
