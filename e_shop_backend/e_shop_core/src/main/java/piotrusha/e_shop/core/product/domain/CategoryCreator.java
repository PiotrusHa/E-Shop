package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;

class CategoryCreator {

    private final CategoryRepository categoryRepository;

    CategoryCreator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void createCategory(CreateProductCategoryDto dto) {
        Category category = create(dto);
        save(category);
    }

    private Category create(CreateProductCategoryDto dto) {
        return new Category(dto.getCategoryName());
    }

    private void save(Category category) {
        categoryRepository.save(category);
    }

}
