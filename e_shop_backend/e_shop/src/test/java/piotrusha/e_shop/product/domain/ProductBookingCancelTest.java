package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.Assertions.assertProductDto;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

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
        product = productFacade.createProduct(CREATE_PRODUCT_DTO).get();
        product2 = productFacade.createProduct(CREATE_PRODUCT_DTO_2).get();

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
    void cancelNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, product.getName());
        AppError.ErrorType expectedErrorType = AppError.ErrorType.VALIDATION;

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(product);
    }

    @Test
    void cancelFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = new CancelProductBookingDto(product.getProductId(), 1);
        CancelProductBookingDto dto2 = new CancelProductBookingDto(product2.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, product2.getName());
        AppError.ErrorType expectedErrorType = AppError.ErrorType.VALIDATION;

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(product);
        assertProductDidNotChange(product2);
    }

    private void assertProductDidNotChange(ProductDto productDto) {
        Option<ProductDto> productOpt = productFacade.findProductByProductId(productDto.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(productDto, productOpt.get());
    }

}
