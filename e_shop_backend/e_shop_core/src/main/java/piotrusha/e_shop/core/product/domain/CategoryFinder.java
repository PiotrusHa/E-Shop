package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;

import java.util.List;

class CategoryFinder {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    CategoryFinder(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    public List<ProductCategoryDto> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categoryConverter.toDto(categories);
    }

}
