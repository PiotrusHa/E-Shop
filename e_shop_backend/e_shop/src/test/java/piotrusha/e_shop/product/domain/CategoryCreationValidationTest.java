package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.createProductCategoryDtoWithEmptyName;
import static piotrusha.e_shop.product.domain.SampleDtosToValidate.createProductCategoryDtoWithNullName;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.stream.Stream;

class CategoryCreationValidationTest extends ProductTest {

    @Test
    void createCategoryWithExistentName() {
        CreateProductCategoryDto dto = prepareCreateExistentCategoryDto();
        String expectedErrorMessage =  String.format("Category with name %s already exists.", dto.getCategoryName());

        Either<AppError, ProductCategoryDto> result = productFacade.createProductCategory(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(ErrorType.CATEGORY_ALREADY_EXISTS, result.getLeft().getErrorType());
    }

    private CreateProductCategoryDto prepareCreateExistentCategoryDto() {
        String categoryName = "Beer";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        productFacade.createProductCategory(dto);
        return dto;
    }

    @ParameterizedTest
    @MethodSource("createCategoryValidationProvider")
    void createCategoryValidationTest(CreateProductCategoryDto dto, String expectedErrorMessage) {
        Either<AppError, ProductCategoryDto> result = productFacade.createProductCategory(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(ErrorType.EMPTY_DTO_FIELD, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> createCategoryValidationProvider() {
        Arguments emptyName = Arguments.of(createProductCategoryDtoWithEmptyName(), "Category name cannot be empty.");
        Arguments nullName = Arguments.of(createProductCategoryDtoWithNullName(), "Category name cannot be empty.");

        return Stream.of(emptyName,
                         nullName
        );
    }

}
