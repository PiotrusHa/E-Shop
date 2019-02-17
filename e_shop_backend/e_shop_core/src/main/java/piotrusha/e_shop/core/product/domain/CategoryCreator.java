package piotrusha.e_shop.core.product.domain;

import com.google.common.base.Strings;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.exception.CategoryValidationException;

class CategoryCreator {

    private final CategoryRepository categoryRepository;

    CategoryCreator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void createCategory(CreateProductCategoryDto dto) {
        validate(dto);
        Category category = create(dto);
        save(category);
    }

    private void validate(CreateProductCategoryDto dto) {
        if (Strings.isNullOrEmpty(dto.getCategoryName())) {
            throw CategoryValidationException.emptyCategoryName();
        }
        if (categoryRepository.existsByName(dto.getCategoryName())) {
            throw CategoryValidationException.categoryNameAlreadyExists(dto.getCategoryName());
        }
    }

    private Category create(CreateProductCategoryDto dto) {
        return new Category(dto.getCategoryName());
    }

    private void save(Category category) {
        categoryRepository.save(category);
    }

}
