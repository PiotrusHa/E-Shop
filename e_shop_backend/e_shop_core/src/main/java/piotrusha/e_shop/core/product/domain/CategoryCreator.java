package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;

class CategoryCreator {

    Category createCategory(CreateProductCategoryDto dto) {
        return new Category(dto.getCategoryName());
    }

}
