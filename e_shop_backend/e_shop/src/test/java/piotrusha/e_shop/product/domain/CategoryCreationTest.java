package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.ProductCategoryDto;

import java.util.stream.Stream;

class CategoryCreationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @Test
    void createCategory() {
        String categoryName = "Beer";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        ProductCategoryDto expected = new ProductCategoryDto(categoryName);

        productFacade.createProductCategory(dto);

        assertTrue(productFacade.findAllProductCategories()
                                .contains(expected));
    }

    @Test
    void createCategoryWithExistentName() {
        String categoryName = "Beer";
        String expectedErrorMessage =  "Category with name " + categoryName + " already exists.";
        CreateProductCategoryDto dto = new CreateProductCategoryDto(categoryName);
        productFacade.createProductCategory(dto);

        Either<AppError, ProductCategoryDto> result = productFacade.createProductCategory(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        Assertions.assertEquals(ErrorType.CATEGORY_ALREADY_EXISTS, result.getLeft().getErrorType());
    }

    @ParameterizedTest
    @MethodSource("createCategoryValidationProvider")
    void createCategoryValidationTest(CreateProductCategoryDto dto, String expectedErrorMessage) {
        Either<AppError, ProductCategoryDto> result = productFacade.createProductCategory(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        Assertions.assertEquals(ErrorType.EMPTY_DTO_FIELD, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> createCategoryValidationProvider() {
        Arguments emptyName = Arguments.of(new CreateProductCategoryDto(""), "Category name cannot be empty.");
        Arguments nullName = Arguments.of(new CreateProductCategoryDto(null), "Category name cannot be empty.");

        return Stream.of(emptyName,
                         nullName
        );
    }

}
