package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.Assertions.assertProductDto;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class ProductTest {

    protected ProductFacade productFacade;

    @BeforeEach
    protected void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    protected void createCategory(String name) {
        CreateProductCategoryDto dto  = new CreateProductCategoryDto(name);
        productFacade.createProductCategory(dto);
    }

    protected ProductDto createProduct(List<String> categories) {
        CreateProductDto createProductDto = new CreateProductDto("Tatra",
                                                                 BigDecimal.TEN,
                                                                 123,
                                                                 "description", categories);
        return productFacade.createProduct(createProductDto).get();
    }

    protected void assertWithProductFromDatabase(BigDecimal productId, ProductDto expected) {
        Option<ProductDto> productOpt = productFacade.findProductByProductId(productId);
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

}
