package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.base.AppError.ErrorType;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

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

        Option<ProductDto> productOpt = productFacade.findProductByProductId(product.getProductId());
        assertTrue(productOpt.isDefined());
        ProductDto productDto = productOpt.get();
        assertEquals(piecesNumberToBook, (int) productDto.getBookedPiecesNumber());
        assertEquals(product.getAvailablePiecesNumber() - piecesNumberToBook, (int) productDto.getAvailablePiecesNumber());
    }

    @Test
    void bookNotEnoughPiecesNumber() {
        Integer piecesNumber = product.getAvailablePiecesNumber() + 1;
        BookProductDto dto = new BookProductDto(product.getProductId(), piecesNumber);
        String expectedErrorMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                                    piecesNumber, product.getName(), product.getAvailablePiecesNumber());
        ErrorType expectedErrorType = ErrorType.VALIDATION;

        Either<AppError, List<Product>> result = productFacade.bookProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertProductDidNotChange(product);
    }

    @Test
    void bookFirstOkSecondNotEnoughPiecesNumber() {
        Integer piecesNumber = product2.getAvailablePiecesNumber() + 1;
        BookProductDto dto = new BookProductDto(product.getProductId(), 1);
        BookProductDto dto2 = new BookProductDto(product2.getProductId(), piecesNumber);
        String expectedErrorMessage = String.format("Cannot book %s pieces of product %s. Available pieces number to book is %s.",
                                                    piecesNumber, product2.getName(), product2.getAvailablePiecesNumber());
        ErrorType expectedErrorType = ErrorType.VALIDATION;

        Either<AppError, List<Product>> result = productFacade.bookProducts(List.of(dto, dto2));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
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
