package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class ProductModificationTest {

    private ProductFacade productFacade;

    private ProductDto productToModify;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();

        String categoryName = "beer";
        CreateProductDto createProductDto = new CreateProductDto("Tatra", BigDecimal.TEN, 123, "description", List.of(categoryName));
        productFacade.createProductCategory(new CreateProductCategoryDto(categoryName));
        productToModify = productFacade.createProduct(createProductDto);
    }

    @Test
    void modifyPrice() {
        BigDecimal newPrice = BigDecimal.valueOf(0.99);
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(productToModify.getProductId())
                                                                  .setProductPrice(newPrice);
        ProductDto expected = productToModify.setPrice(newPrice);

        productFacade.modifyProduct(modifyProductDto);

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isPresent());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyAvailablePiecesNumber() {
        Integer newAvailablePiecesNumber = 1912;
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(productToModify.getProductId())
                                                                  .setProductAvailablePiecesNumber(newAvailablePiecesNumber);
        ProductDto expected = productToModify.setAvailablePiecesNumber(newAvailablePiecesNumber);

        productFacade.modifyProduct(modifyProductDto);

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isPresent());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyAvailablePiecesNumberNegativePiecesNumber() {
        Integer newAvailablePiecesNumber = -100;
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(productToModify.getProductId())
                                                                  .setProductAvailablePiecesNumber(newAvailablePiecesNumber);
        String expectedMessage = "Product available pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.modifyProduct(modifyProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void modifyDescription() {
        String newDescription = "new description";
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(productToModify.getProductId())
                                                                  .setProductDescription(newDescription);
        ProductDto expected = productToModify.setDescription(newDescription);

        productFacade.modifyProduct(modifyProductDto);

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isPresent());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyNonexistentProduct() {
        BigDecimal nonexistentProductId = BigDecimal.valueOf(100);
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(nonexistentProductId);
        String expectedMessage = "Product with productId " + nonexistentProductId + " not found";

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> productFacade.modifyProduct(modifyProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void modifyNullProductId() {
        ModifyProductDto modifyProductDto = new ModifyProductDto().setProductId(null);
        String expectedMessage = "Product id cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.modifyProduct(modifyProductDto));
        assertEquals(expectedMessage, e.getMessage());
    }

}
