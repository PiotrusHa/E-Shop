package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.sellProductDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.sellProductDtoWithProductId;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.sellProductDtoWithZeroPiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.sellProductDtoWithoutPiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.sellProductDtoWithoutProductId;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductSellingValidationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @ParameterizedTest
    @MethodSource("sellValidationProvider")
    void sellValidationTest(SellProductDto dto, String expectedErrorMessage, AppError.ErrorType expectedErrorType) {
        Either<AppError, List<Product>> result = productFacade.sellProducts(List.of(dto));

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> sellValidationProvider() {
        Arguments withoutProductId = Arguments.of(sellProductDtoWithoutProductId(),
                                                  "Product id cannot be empty.", AppError.ErrorType.VALIDATION);
        BigDecimal nonexistentId = BigDecimal.TEN;
        Arguments nonexistentProduct = Arguments.of(sellProductDtoWithProductId(nonexistentId),
                                                    "Product with productId " + nonexistentId + " not found", AppError.ErrorType.NOT_FOUND);

        Arguments withoutPiecesNumber = Arguments.of(sellProductDtoWithoutPiecesNumber(),
                                                     "Product pieces number has to be greater than zero.", AppError.ErrorType.VALIDATION);
        Arguments negativePiecesNumber = Arguments.of(sellProductDtoWithNegativePiecesNumber(),
                                                      "Product pieces number has to be greater than zero.", AppError.ErrorType.VALIDATION);
        Arguments zeroPiecesNumber = Arguments.of(sellProductDtoWithZeroPiecesNumber(),
                                                  "Product pieces number has to be greater than zero.", AppError.ErrorType.VALIDATION);

        return Stream.of(withoutProductId,
                         nonexistentProduct,
                         withoutPiecesNumber,
                         negativePiecesNumber,
                         zeroPiecesNumber
        );
    }

}
