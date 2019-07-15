package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.sellProductDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.sellProductDtoWithProductId;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.sellProductDtoWithZeroPiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.sellProductDtoWithoutPiecesNumber;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.sellProductDtoWithoutProductId;

import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.ProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductSellingValidationTest extends ProductTest {

    @ParameterizedTest
    @MethodSource("sellValidationProvider")
    void sellValidationTest(SellProductDto dto, String expectedErrorMessage, ErrorType expectedErrorType) {
        Either<AppError, List<ProductDto>> result = productFacade.sellProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> sellValidationProvider() {
        Arguments withoutProductId = Arguments.of(sellProductDtoWithoutProductId(),
                                                  "Product id cannot be empty.",
                                                  ErrorType.EMPTY_DTO_FIELD);
        BigDecimal nonexistentId = BigDecimal.TEN;
        Arguments nonexistentProduct = Arguments.of(sellProductDtoWithProductId(nonexistentId),
                                                    "Product with productId " + nonexistentId + " not found.",
                                                    ErrorType.PRODUCT_NOT_FOUND);

        Arguments withoutPiecesNumber = Arguments.of(sellProductDtoWithoutPiecesNumber(),
                                                     "Product pieces number has to be greater than zero.",
                                                     ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments negativePiecesNumber = Arguments.of(sellProductDtoWithNegativePiecesNumber(),
                                                      "Product pieces number has to be greater than zero.",
                                                      ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments zeroPiecesNumber = Arguments.of(sellProductDtoWithZeroPiecesNumber(),
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
