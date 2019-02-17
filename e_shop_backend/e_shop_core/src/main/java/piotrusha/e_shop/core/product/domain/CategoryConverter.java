package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

class CategoryConverter {

    ProductCategoryDto toDto(Category category) {
        return new ProductCategoryDto(category.getName());
    }

    List<ProductCategoryDto> toDto(List<Category> categories) {
        return categories.stream()
                         .map(this::toDto)
                         .collect(Collectors.toList());
    }

}
