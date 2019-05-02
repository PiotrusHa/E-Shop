package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

class ProductBookingTest extends ProductTest {

    private ProductDto productToBook;
    private ProductDto productToBook2;

    @BeforeEach
    protected void init() {
        super.init();

        productToBook = createProduct();
        productToBook2 = createProduct2();
    }

    @Test
    void book() {
        BookProductDto bookProductDto = bookProductDto(productToBook.getProductId(), 23);
        ProductDto expectedChangedProduct = expectedProductDto(productToBook, bookProductDto);

        Either<AppError, List<ProductDto>> result = productFacade.bookProducts(List.of(bookProductDto));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertWithProductFromDatabase(productToBook.getProductId(), expectedChangedProduct);
    }

    @Test
    void bookTwoProducts() {
        BookProductDto bookProductDto = bookProductDto(productToBook.getProductId(), 12);
        BookProductDto bookProductDto2 = bookProductDto(productToBook2.getProductId(), 45);
        ProductDto expectedChangedProduct = expectedProductDto(productToBook, bookProductDto);
        ProductDto expectedChangedProduct2 = expectedProductDto(productToBook2, bookProductDto2);

        Either<AppError, List<ProductDto>> result = productFacade.bookProducts(List.of(bookProductDto, bookProductDto2));

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get().get(0));
        assertProductDto(expectedChangedProduct2, result.get().get(1));
        assertWithProductFromDatabase(productToBook.getProductId(), expectedChangedProduct);
        assertWithProductFromDatabase(productToBook2.getProductId(), expectedChangedProduct2);
    }

    @Test
    void bookNotEnoughPiecesNumber() {
        int piecesNumber = productToBook.getAvailablePiecesNumber() + 1;
        BookProductDto bookProductDto = bookProductDto(productToBook.getProductId(), piecesNumber);
        String expectedErrorMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                                    piecesNumber, productToBook.getName(), productToBook.getAvailablePiecesNumber());
        ErrorType expectedErrorType = ErrorType.CANNOT_BOOK_PRODUCT;

        Either<AppError, List<ProductDto>> result = productFacade.bookProducts(List.of(bookProductDto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToBook);
    }

    @Test
    void bookFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = productToBook2.getAvailablePiecesNumber() + 1;
        BookProductDto dto =  bookProductDto(productToBook.getProductId(), 1);
        BookProductDto dto2 = bookProductDto(productToBook2.getProductId(), piecesNumber);
        String expectedErrorMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                                    piecesNumber, productToBook2.getName(), productToBook2.getAvailablePiecesNumber());
        ErrorType expectedErrorType = ErrorType.CANNOT_BOOK_PRODUCT;

        Either<AppError, List<ProductDto>> result = productFacade.bookProducts(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(productToBook);
        assertProductDidNotChange(productToBook2);
    }

    private ProductDto expectedProductDto(ProductDto currentProduct, BookProductDto dto) {
        return currentProduct.toBuilder()
                             .availablePiecesNumber(currentProduct.getAvailablePiecesNumber() - dto.getPiecesNumber())
                             .bookedPiecesNumber(currentProduct.getBookedPiecesNumber() + dto.getPiecesNumber())
                             .build();
    }

    private BookProductDto bookProductDto(BigDecimal productId, int piecesNumber) {
        return new BookProductDto(productId, piecesNumber);
    }

}
