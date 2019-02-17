package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class ProductCreationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @Test
    void create() {
        String productName = "Tatra";
        BigDecimal price = BigDecimal.ONE;
        Integer availablePiecesNumber = 10;
        String description = "description";
        String categoryName = "beer";
        CreateProductDto createProductDto =
                new CreateProductDto(productName, price, availablePiecesNumber, description, List.of(categoryName));
        productFacade.createProductCategory(new CreateProductCategoryDto(categoryName));
        ProductDto expected = new ProductDto().setProductId(BigDecimal.ONE)
                                              .setName(productName)
                                              .setPrice(price)
                                              .setAvailablePiecesNumber(availablePiecesNumber)
                                              .setBookedPiecesNumber(0)
                                              .setSoldPiecesNumber(0)
                                              .setDescription(description)
                                              .setCategories(List.of(categoryName));

        ProductDto createdProduct = productFacade.createProduct(createProductDto);

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(createdProduct.getProductId());
        assertTrue(productOpt.isPresent());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void createWithEmptyName() {
        CreateProductDto createProductDto = new CreateProductDto("", BigDecimal.ONE, 1, "", List.of("beer"));
        String expectedMessage = "Product name cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.createProduct(createProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createWithEmptyPrice() {
        CreateProductDto createProductDto = new CreateProductDto("Tatra", null, 1, "", List.of("beer"));
        String expectedMessage = "Product price cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.createProduct(createProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createWithEmptyAvailablePiecesNumber() {
        CreateProductDto createProductDto = new CreateProductDto("Tatra", BigDecimal.ONE, null, "", List.of("beer"));
        String expectedMessage = "Product available pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.createProduct(createProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createWithNegativeAvailablePiecesNumber() {
        CreateProductDto createProductDto = new CreateProductDto("Tatra", BigDecimal.ONE, -10, "", List.of("beer"));
        String expectedMessage = "Product available pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.createProduct(createProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createWithNonexistentCategoryName() {
        String categoryName = "beer";
        CreateProductDto createProductDto = new CreateProductDto("Tatra", BigDecimal.ONE, 1, "", List.of(categoryName));
        String expectedMessage = "Category with name " + categoryName + " does not exists.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.createProduct(createProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

}
