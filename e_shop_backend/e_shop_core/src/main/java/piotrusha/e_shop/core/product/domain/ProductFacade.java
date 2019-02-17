package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;

import java.util.List;

public class ProductFacade {

    private final CategoryCreator categoryCreator;
    private final CategoryFinder categoryFinder;

    ProductFacade(CategoryCreator categoryCreator, CategoryFinder categoryFinder) {
        this.categoryCreator = categoryCreator;
        this.categoryFinder = categoryFinder;
    }

    public void createProductCategory(CreateProductCategoryDto createProductCategoryDto) {
        categoryCreator.createCategory(createProductCategoryDto);
    }

    public List<ProductCategoryDto> findAllProductCategories() {
        return categoryFinder.findAll();
    }

}
