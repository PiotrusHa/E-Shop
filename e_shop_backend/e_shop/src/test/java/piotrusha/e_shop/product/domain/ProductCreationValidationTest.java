package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.util.stream.Stream;

class ProductCreationValidationTest extends ProductTest {

    @ParameterizedTest
    @MethodSource("createProductValidationProvider")
    void createProductValidationTest(CreateProductDto dto, String expectedMessage, ErrorType expectedErrorType) {
        Either<AppError, ProductDto> result = productFacade.createProduct(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
    }

    private static Stream<Arguments> createProductValidationProvider() {
        Arguments emptyName = Arguments.of(SampleDtosToValidate.createProductDtoWithoutName(),
                                           "Product name cannot be empty.",
                                           ErrorType.EMPTY_DTO_FIELD);
        Arguments emptyPrice = Arguments.of(SampleDtosToValidate.createProductDtoWithoutPrice(),
                                            "Product price cannot be empty.",
                                            ErrorType.EMPTY_DTO_FIELD);
        Arguments emptyAvailablePiecesNumber = Arguments.of(SampleDtosToValidate.createProductDtoWithoutAvailablePiecesNumber(),
                                                            "Product pieces number has to be greater than zero.",
                                                            ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments negativeAvailablePiecesNumber = Arguments.of(SampleDtosToValidate.createProductDtoWithNegativeAvailablePiecesNumber(),
                                                               "Product pieces number has to be greater than zero.",
                                                               ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments zeroAvailablePiecesNumber = Arguments.of(SampleDtosToValidate.createProductDtoWithZeroAvailablePiecesNumber(),
                                                           "Product pieces number has to be greater than zero.",
                                                           ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        String nonexistentCategoryName = "beer";
        Arguments nonexistentCategory = Arguments.of(SampleDtosToValidate.createProductDtoWithNonexistentCategory(nonexistentCategoryName),
                                                     "Category with name " + nonexistentCategoryName + " does not exists.",
                                                     ErrorType.CATEGORY_DOES_NOT_EXISTS);

        return Stream.of(emptyName,
                         emptyPrice,
                         emptyAvailablePiecesNumber,
                         negativeAvailablePiecesNumber,
                         zeroAvailablePiecesNumber,
                         nonexistentCategory
        );
    }

}
