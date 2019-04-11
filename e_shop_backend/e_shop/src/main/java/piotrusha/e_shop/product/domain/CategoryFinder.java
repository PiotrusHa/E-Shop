package piotrusha.e_shop.product.domain;

import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.List;

class CategoryFinder {

    private final CategoryRepository categoryRepository;

    CategoryFinder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<ProductCategoryDto> findAll() {
        return categoryRepository.findAll();
    }

}
