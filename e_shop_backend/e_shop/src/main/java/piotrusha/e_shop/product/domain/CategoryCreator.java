package piotrusha.e_shop.product.domain;

import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;

class CategoryCreator {

    Category createCategory(CreateProductCategoryDto dto) {
        return new Category(dto.getCategoryName());
    }

}
