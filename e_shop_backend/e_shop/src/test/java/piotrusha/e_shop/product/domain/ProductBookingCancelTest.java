package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

class ProductBookingCancelTest extends ProductTest {

    private ProductDto productToCancel;
    private ProductDto productToCancel2;

    @BeforeEach
    protected void init() {
        super.init();

        productToCancel = createBookedProduct();
        productToCancel2 = createBookedProduct();
    }

    @Test
    void cancel() {
        CancelProductBookingDto cancelProductBookingDto = cancelProductBookingDto(productToCancel.getProductId(), 23);
        ProductDto expectedChangedProduct = expectedProductDto(productToCancel, cancelProductBookingDto);

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(cancelProductBookingDto));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertWithProductFromDatabase(productToCancel.getProductId(), expectedChangedProduct);
    }

    @Test
    void cancelTwoProducts() {
        CancelProductBookingDto cancelProductBookingDto = cancelProductBookingDto(productToCancel.getProductId(), 20);
        CancelProductBookingDto cancelProductBookingDto2 = cancelProductBookingDto(productToCancel2.getProductId(), 10);
        ProductDto expectedChangedProduct = expectedProductDto(productToCancel, cancelProductBookingDto);
        ProductDto expectedChangedProduct2 = expectedProductDto(productToCancel2, cancelProductBookingDto2);

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(cancelProductBookingDto, cancelProductBookingDto2));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertProductDto(expectedChangedProduct2, result.get().get(1));
        assertWithProductFromDatabase(productToCancel.getProductId(), expectedChangedProduct);
        assertWithProductFromDatabase(productToCancel2.getProductId(), expectedChangedProduct2);
    }

    @Test
    void cancelNotEnoughPiecesNumber() {
        int piecesNumber = productToCancel.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = cancelProductBookingDto(productToCancel.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, productToCancel.getName());
        ErrorType expectedErrorType = ErrorType.CANNOT_CANCEL_PRODUCT_BOOKING;

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToCancel);
    }

    @Test
    void cancelFirstOkSecondNotEnoughPiecesNumber() {
        int piecesNumber = productToCancel2.getAvailablePiecesNumber() + 1;
        CancelProductBookingDto dto = cancelProductBookingDto(productToCancel.getProductId(), 1);
        CancelProductBookingDto dto2 = cancelProductBookingDto(productToCancel2.getProductId(), piecesNumber);
        String expectedMessage = String.format("Cannot cancel booking %s pieces of product %s.", piecesNumber, productToCancel2.getName());
        ErrorType expectedErrorType = ErrorType.CANNOT_CANCEL_PRODUCT_BOOKING;

        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToCancel);
        assertProductDidNotChange(productToCancel2);
    }

    private CancelProductBookingDto cancelProductBookingDto(BigDecimal productId, int piecesNumber) {
        return new CancelProductBookingDto(productId, piecesNumber);
    }

    private ProductDto expectedProductDto(ProductDto currentProduct, CancelProductBookingDto dto) {
        return currentProduct.toBuilder()
                             .availablePiecesNumber(currentProduct.getAvailablePiecesNumber() + dto.getPiecesNumber())
                             .bookedPiecesNumber(currentProduct.getBookedPiecesNumber() - dto.getPiecesNumber())
                             .build();
    }

}
