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
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class ProductBookingTest {

    private static final CreateProductDto CREATE_PRODUCT_DTO = new CreateProductDto("Name", BigDecimal.TEN, 2, "", List.of());
    private static final CreateProductDto CREATE_PRODUCT_DTO_2 = new CreateProductDto("Name2", BigDecimal.TEN, 1, "", List.of());

    private ProductFacade productFacade;
    private ProductDto product;
    private ProductDto product2;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
        product = productFacade.createProduct(CREATE_PRODUCT_DTO);
        product2 = productFacade.createProduct(CREATE_PRODUCT_DTO_2);
    }

    @Test
    void book() {
        int piecesNumberToBook = 1;
        BookProductDto dto = new BookProductDto(product.getProductId(), piecesNumberToBook);

        productFacade.bookProducts(List.of(dto));

        Optional<ProductDto> productOpt = productFacade.findProductByProductId(product.getProductId());
        assertTrue(productOpt.isPresent());
        ProductDto productDto = productOpt.get();
        assertEquals(piecesNumberToBook, (int) productDto.getBookedPiecesNumber());
        assertEquals(product.getAvailablePiecesNumber() - piecesNumberToBook, (int) productDto.getAvailablePiecesNumber());
    }

    @Test
    void bookWithoutProductId() {
        BookProductDto dto = new BookProductDto(null, 1);
        String expectedMessage = "Product id cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void bookNonexistentProduct() {
        BigDecimal id = BigDecimal.TEN;
        BookProductDto dto = new BookProductDto(id, 1);
        String expectedMessage = "Product with productId " + id + " not found";

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void bookWithoutPiecesNumber() {
        BookProductDto dto = new BookProductDto(product.getProductId(), null);
        String expectedMessage = "Product book pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void bookNegativePiecesNumber() {
        BookProductDto dto = new BookProductDto(product.getProductId(), -2);
        String expectedMessage = "Product book pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void bookZeroPiecesNumber() {
        BookProductDto dto = new BookProductDto(product.getProductId(), 0);
        String expectedMessage = "Product book pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void bookNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        BookProductDto dto = new BookProductDto(product.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                               piecesNumber, product.getName(), product.getAvailablePiecesNumber());

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void bookFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        BookProductDto dto = new BookProductDto(product.getProductId(), 1);
        BookProductDto dto2 = new BookProductDto(product2.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                               piecesNumber, product2.getName(), product2.getAvailablePiecesNumber());

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.bookProducts(List.of(dto, dto2)));
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
