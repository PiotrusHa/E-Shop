package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.bookProductDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.bookProductDtoWithProductId;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.bookProductDtoWithZeroPiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.bookProductDtoWithoutPiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.bookProductDtoWithoutProductId;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.base.AppError.ErrorType;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductBookingValidationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @ParameterizedTest
    @MethodSource("bookValidationProvider")
    void bookValidationTest(BookProductDto dto, String expectedErrorMessage, ErrorType expectedErrorType) {
        Either<AppError, List<ProductDto>> result = productFacade.bookProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> bookValidationProvider() {
        Arguments withoutProductId = Arguments.of(bookProductDtoWithoutProductId(),
                                                  "Product id cannot be empty.", ErrorType.VALIDATION);
        BigDecimal nonexistentId = BigDecimal.TEN;
        Arguments nonexistentProduct = Arguments.of(bookProductDtoWithProductId(nonexistentId),
                                                    "Product with productId " + nonexistentId + " not found", ErrorType.NOT_FOUND);

        Arguments withoutPiecesNumber = Arguments.of(bookProductDtoWithoutPiecesNumber(),
                                                     "Product pieces number has to be greater than zero.", ErrorType.VALIDATION);
        Arguments negativePiecesNumber = Arguments.of(bookProductDtoWithNegativePiecesNumber(),
                                                      "Product pieces number has to be greater than zero.", ErrorType.VALIDATION);
        Arguments zeroPiecesNumber = Arguments.of(bookProductDtoWithZeroPiecesNumber(),
                                                  "Product pieces number has to be greater than zero.", ErrorType.VALIDATION);

        return Stream.of(withoutProductId,
                         nonexistentProduct,
                         withoutPiecesNumber,
                         negativePiecesNumber,
                         zeroPiecesNumber
        );
    }

}
