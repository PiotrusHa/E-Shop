package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class ProductSellingTest {

    private static final CreateProductDto CREATE_PRODUCT_DTO = new CreateProductDto("Name", BigDecimal.TEN, 20, "", List.of());
    private static final CreateProductDto CREATE_PRODUCT_DTO_2 = new CreateProductDto("Name2", BigDecimal.TEN, 11, "", List.of());

    private ProductFacade productFacade;
    private ProductDto product;
    private ProductDto product2;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
        product = productFacade.createProduct(CREATE_PRODUCT_DTO);
        product2 = productFacade.createProduct(CREATE_PRODUCT_DTO_2);

        BookProductDto bookProductDto = new BookProductDto(product.getProductId(), 2);
        BookProductDto bookProduct2Dto = new BookProductDto(product2.getProductId(), 5);
        productFacade.bookProducts(List.of(bookProductDto, bookProduct2Dto));

        product = productFacade.findProductByProductId(product.getProductId())
                               .get();
        product2 = productFacade.findProductByProductId(product2.getProductId())
                                .get();
    }

    @Test
    void sell() {
        int piecesNumberToSell = 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), piecesNumberToSell);

        productFacade.sellProducts(List.of(dto));

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(product.getProductId());
        assertTrue(productOpt.isPresent());
        ProductDto productDto = productOpt.get();
        assertEquals(product.getBookedPiecesNumber() - piecesNumberToSell, (int) productDto.getBookedPiecesNumber());
        assertEquals(product.getSoldPiecesNumber() + piecesNumberToSell, (int) productDto.getSoldPiecesNumber());
    }

    @Test
    void sellWithoutProductId() {
        SellProductDto dto = new SellProductDto(null, 1);
        String expectedMessage = "Product id cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void sellNonexistentProduct() {
        BigDecimal id = BigDecimal.TEN;
        SellProductDto dto = new SellProductDto(id, 1);
        String expectedMessage = "Product with productId " + id + " not found";

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void sellWithoutPiecesNumber() {
        SellProductDto dto = new SellProductDto(product.getProductId(), null);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void sellNegativePiecesNumber() {
        SellProductDto dto = new SellProductDto(product.getProductId(), -2);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void sellZeroPiecesNumber() {
        SellProductDto dto = new SellProductDto(product.getProductId(), 0);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void sellNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), piecesNumber);
        String expectedMessage =
                String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.", piecesNumber, product.getName(),
                              product.getBookedPiecesNumber());

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void sellFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        SellProductDto dto = new SellProductDto(product.getProductId(), 1);
        SellProductDto dto2 = new SellProductDto(product2.getProductId(), piecesNumber);
        String expectedMessage =
                String.format("Cannot sell %s pieces of product %s. Currently booked pieces number is %s.", piecesNumber,
                              product2.getName(), product2.getBookedPiecesNumber());

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.sellProducts(List.of(dto, dto2)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
        assertProductDidNotChange(product2);
    }

    private void assertProductDidNotChange(ProductDto productDto) {
        Optional<ProductDto> productOpt = productFacade.findProductByProductId(productDto.getProductId());
        assertTrue(productOpt.isPresent());
        assertProductDto(productDto, productOpt.get());
    }

}
