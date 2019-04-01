package piotrusha.e_shop.product.domain.dto_validation_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.dto_validation_test.SampleDtosToValidate.modifyProductDtoWithNegativeAvailablePiecesNumber;
import static piotrusha.e_shop.product.domain.dto_validation_test.SampleDtosToValidate.modifyProductDtoWithNonexistentCategory;
import static piotrusha.e_shop.product.domain.dto_validation_test.SampleDtosToValidate.modifyProductDtoWithProductId;
import static piotrusha.e_shop.product.domain.dto_validation_test.SampleDtosToValidate.modifyProductDtoWithZeroAvailablePiecesNumber;

import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.ProductTest;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.stream.Stream;

class ProductModificationValidationTest extends ProductTest {

    @ParameterizedTest
    @MethodSource("modifyProductValidationProvider")
    void modifyProductValidationTest(ModifyProductDto dto, String expectedMessage, AppError.ErrorType expectedErrorType) {
        Either<AppError, ProductDto> result = productFacade.modifyProduct(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> modifyProductValidationProvider() {
        Arguments negativeAvailablePiecesNumber = Arguments.of(modifyProductDtoWithNegativeAvailablePiecesNumber(),
                                                               "Product pieces number has to be greater than zero.",
                                                               AppError.ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments zeroAvailablePiecesNumber = Arguments.of(modifyProductDtoWithZeroAvailablePiecesNumber(),
                                                           "Product pieces number has to be greater than zero.",
                                                           AppError.ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments nullProductId = Arguments.of(modifyProductDtoWithProductId(null),
                                               "Product id cannot be empty.",
                                               AppError.ErrorType.EMPTY_DTO_FIELD);
        BigDecimal nonexistentProductId = BigDecimal.valueOf(1000);
        Arguments nonexistentProduct = Arguments.of(modifyProductDtoWithProductId(nonexistentProductId),
                                                    "Product with productId " + nonexistentProductId + " not found.",
                                                    AppError.ErrorType.PRODUCT_NOT_FOUND);
        String nonexistentCategoryName = "example";
        Arguments nonexistentCategory = Arguments.of(modifyProductDtoWithNonexistentCategory(nonexistentCategoryName),
                                                     "Category with name " + nonexistentCategoryName + " does not exists.",
                                                     AppError.ErrorType.CATEGORY_DOES_NOT_EXISTS);

        return Stream.of(negativeAvailablePiecesNumber,
                         zeroAvailablePiecesNumber,
                         nullProductId,
                         nonexistentProduct,
                         nonexistentCategory
        );
    }

}
