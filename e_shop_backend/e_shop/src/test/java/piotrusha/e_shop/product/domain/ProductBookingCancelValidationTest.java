package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.cancelProductBookingDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.cancelProductBookingDtoWithProductId;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.cancelProductBookingDtoWithZeroPiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.cancelProductBookingDtoWithoutPiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.cancelProductBookingDtoWithoutProductId;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductBookingCancelValidationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @ParameterizedTest
    @MethodSource("cancelBookingValidationProvider")
    void cancelBookingValidationTest(CancelProductBookingDto dto, String expectedErrorMessage, ErrorType expectedErrorType) {
        Either<AppError, List<ProductDto>> result = productFacade.cancelBooking(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> cancelBookingValidationProvider() {
        Arguments withoutProductId = Arguments.of(cancelProductBookingDtoWithoutProductId(),
                                                  "Product id cannot be empty.",
                                                  ErrorType.EMPTY_DTO_FIELD);
        BigDecimal nonexistentId = BigDecimal.TEN;
        Arguments nonexistentProduct = Arguments.of(cancelProductBookingDtoWithProductId(nonexistentId),
                                                    "Product with productId " + nonexistentId + " not found.",
                                                    ErrorType.PRODUCT_NOT_FOUND);

        Arguments withoutPiecesNumber = Arguments.of(cancelProductBookingDtoWithoutPiecesNumber(),
                                                     "Product pieces number has to be greater than zero.",
                                                     ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments negativePiecesNumber = Arguments.of(cancelProductBookingDtoWithNegativePiecesNumber(),
                                                      "Product pieces number has to be greater than zero.",
                                                      ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments zeroPiecesNumber = Arguments.of(cancelProductBookingDtoWithZeroPiecesNumber(),
                                                  "Product pieces number has to be greater than zero.",
                                                  ErrorType.NUMBER_SHOULD_BE_POSITIVE);

        return Stream.of(withoutProductId,
                         nonexistentProduct,
                         withoutPiecesNumber,
                         negativePiecesNumber,
                         zeroPiecesNumber
        );
    }

}
