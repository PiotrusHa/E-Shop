package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.ProductCategoryDto;
import piotrusha.e_shop.core.product.domain.exception.CategoryValidationException;

class CategoryCreationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @Test
    void createCategory() {
        String categoryName = "Beer";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        ProductCategoryDto expected = new ProductCategoryDto(categoryName);

        productFacade.createProductCategory(dto);

        assertTrue(productFacade.findAllProductCategories()
                                .contains(expected));
    }

    @Test
    void createCategoryWithEmptyName() {
        String categoryName = "";
        String expectedExceptionMessage = "Category name cannot be empty.";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);

        CategoryValidationException e = assertThrows(CategoryValidationException.class, () -> productFacade.createProductCategory(dto));
        assertEquals(expectedExceptionMessage, e.getMessage());
    }

    @Test
    void createCategoryWithExistentName() {
        String categoryName = "Beer";
        String expectedExceptionMessage = "Category with name " + categoryName + " already exists.";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        productFacade.createProductCategory(dto);

        CategoryValidationException e = assertThrows(CategoryValidationException.class, () -> productFacade.createProductCategory(dto));
        assertEquals(expectedExceptionMessage, e.getMessage());
    }

}
