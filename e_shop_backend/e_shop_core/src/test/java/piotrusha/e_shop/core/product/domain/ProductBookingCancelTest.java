package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;

class ProductBookingCancelTest {

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

        BookProductDto bookProductDto = new BookProductDto(product.getProductId(), 1);
        BookProductDto bookProduct2Dto = new BookProductDto(product2.getProductId(), 5);
        productFacade.bookProducts(List.of(bookProductDto, bookProduct2Dto));

        product = productFacade.findProductByProductId(product.getProductId())
                               .get();
        product2 = productFacade.findProductByProductId(product2.getProductId())
                                .get();
    }

    @Test
    void cancel() {
        int piecesNumberToCancel = 1;
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), piecesNumberToCancel);

        productFacade.cancelBooking(List.of(dto));

        Option<ProductDto> productOpt = productFacade.findProductByProductId(product.getProductId());
        assertTrue(productOpt.isDefined());
        ProductDto productDto = productOpt.get();
        assertEquals(product.getBookedPiecesNumber() - piecesNumberToCancel, (int) productDto.getBookedPiecesNumber());
        assertEquals(product.getAvailablePiecesNumber() + piecesNumberToCancel, (int) productDto.getAvailablePiecesNumber());
    }

    @Test
    void cancelWithoutProductId() {
        CancelProductBookingDto dto = new CancelProductBookingDto(null, 1);
        String expectedMessage = "Product id cannot be empty.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void cancelNonexistentProduct() {
        BigDecimal id = BigDecimal.TEN;
        CancelProductBookingDto dto = new CancelProductBookingDto(id, 1);
        String expectedMessage = "Product with productId " + id + " not found";

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void cancelWithoutPiecesNumber() {
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), null);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void cancelNegativePiecesNumber() {
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), -2);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void cancelZeroPiecesNumber() {
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), 0);
        String expectedMessage = "Product pieces number has to be greater than zero.";

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void cancelNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, product.getName());

        ProductValidationException e = assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
    }

    @Test
    void cancelFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), 1);
        CancelProductBookingDto dto2 = new CancelProductBookingDto(product2.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, product2.getName());

        ProductValidationException e =
                assertThrows(ProductValidationException.class, () -> productFacade.cancelBooking(List.of(dto, dto2)));
        assertEquals(expectedMessage, e.getMessage());
        assertProductDidNotChange(product);
        assertProductDidNotChange(product2);
    }

    private void assertProductDidNotChange(ProductDto productDto) {
        Option<ProductDto> productOpt = productFacade.findProductByProductId(productDto.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(productDto, productOpt.get());
    }

}
