package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductCreationTest {

    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    @Test
    void create() {
        String productName = "Tatra";
        BigDecimal price = BigDecimal.ONE;
        Integer availablePiecesNumber = 10;
        String description = "description";
        String categoryName = "beer";
        CreateProductDto createProductDto =
                new CreateProductDto(productName, price, availablePiecesNumber, description, List.of(categoryName));
        productFacade.createProductCategory(new CreateProductCategoryDto(categoryName));
        ProductDto expected = new ProductDto().setProductId(BigDecimal.ONE)
                                              .setName(productName)
                                              .setPrice(price)
                                              .setAvailablePiecesNumber(availablePiecesNumber)
                                              .setBookedPiecesNumber(0)
                                              .setSoldPiecesNumber(0)
                                              .setDescription(description)
                                              .setCategories(List.of(categoryName));

        Either<AppError, ProductDto> createdProduct = productFacade.createProduct(createProductDto);

        assertTrue(createdProduct.isRight());
        Option<ProductDto> productOpt = productFacade.findProductByProductId(createdProduct.get().getProductId());
        assertTrue(productOpt.isDefined());
        Assertions.assertProductDto(expected, productOpt.get());
    }

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
