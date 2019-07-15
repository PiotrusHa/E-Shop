package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

class CategoryCreationTest extends ProductTest {

    @Test
    void createCategory() {
        String categoryName = "Beer";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        ProductCategoryDto expected = new ProductCategoryDto(categoryName);

        productFacade.createProductCategory(dto);

        assertTrue(productFacade.findAllProductCategories()
                                .contains(expected));
    }

}
