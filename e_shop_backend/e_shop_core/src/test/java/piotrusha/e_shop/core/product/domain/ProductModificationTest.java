package piotrusha.e_shop.core.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.product.domain.Assertions.assertProductDto;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.modifyProductDtoWithNegativeAvailablePiecesNumber;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.modifyProductDtoWithNonexistentCategory;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.modifyProductDtoWithProductId;
import static piotrusha.e_shop.core.product.domain.SampleDtosToValidate.modifyProductDtoWithZeroAvailablePiecesNumber;

import io.vavr.control.Either;
import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.base.AppError.ErrorType;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class ProductModificationTest {

    private static final String ASSIGNED_CATEGORY = "beer";
    private static final String CATEGORY_TO_ASSIGN1 = "category1";
    private static final String CATEGORY_TO_ASSIGN2 = "category2";

    private ProductFacade productFacade;

    private ProductDto productToModify;

    @BeforeEach
    void init() {
        productFacade = new ProductConfiguration().productFacade();

        CreateProductDto createProductDto = new CreateProductDto("Tatra", BigDecimal.TEN, 123, "description", List.of(ASSIGNED_CATEGORY));
        productFacade.createProductCategory(new CreateProductCategoryDto(ASSIGNED_CATEGORY));
        productFacade.createProductCategory(new CreateProductCategoryDto(CATEGORY_TO_ASSIGN1));
        productFacade.createProductCategory(new CreateProductCategoryDto(CATEGORY_TO_ASSIGN2));
        productToModify = productFacade.createProduct(createProductDto).get();
    }

    @Test
    void modifyPrice() {
        BigDecimal newPrice = BigDecimal.valueOf(0.99);
        ModifyProductDto modifyProductDto = new ModifyProductDto(productToModify.getProductId()).setProductPrice(newPrice);
        ProductDto expected = productToModify.setPrice(newPrice);

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyAvailablePiecesNumber() {
        Integer newAvailablePiecesNumber = 1912;
        ModifyProductDto modifyProductDto =
                new ModifyProductDto(productToModify.getProductId()).setProductAvailablePiecesNumber(newAvailablePiecesNumber);
        ProductDto expected = productToModify.setAvailablePiecesNumber(newAvailablePiecesNumber);

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyDescription() {
        String newDescription = "new description";
        ModifyProductDto modifyProductDto = new ModifyProductDto(productToModify.getProductId()).setProductDescription(newDescription);
        ProductDto expected = productToModify.setDescription(newDescription);

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyAssignCategory() {
        ModifyProductDto modifyProductDto = new ModifyProductDto(productToModify.getProductId()).setProductCategoriesToAssign(
                List.of(CATEGORY_TO_ASSIGN1, CATEGORY_TO_ASSIGN2));
        ProductDto expected = productToModify.setCategories(List.of(CATEGORY_TO_ASSIGN1, CATEGORY_TO_ASSIGN2, ASSIGNED_CATEGORY));

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyAssignAlreadyAssignedCategory() {
        ModifyProductDto modifyProductDto =
                new ModifyProductDto(productToModify.getProductId()).setProductCategoriesToAssign(List.of(ASSIGNED_CATEGORY));
        ProductDto expected = productToModify.setCategories(List.of(ASSIGNED_CATEGORY));

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyUnassignCategory() {
        ModifyProductDto modifyProductDto =
                new ModifyProductDto(productToModify.getProductId()).setProductCategoriesToUnassign(List.of(ASSIGNED_CATEGORY));
        ProductDto expected = productToModify.setCategories(List.of());

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyUnassignNonexistentCategory() {
        ModifyProductDto modifyProductDto =
                new ModifyProductDto(productToModify.getProductId()).setProductCategoriesToUnassign(List.of("nonexistent category"));
        ProductDto expected = productToModify.setCategories(List.of(ASSIGNED_CATEGORY));

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @Test
    void modifyUnassignNotAssignedCategory() {
        ModifyProductDto modifyProductDto =
                new ModifyProductDto(productToModify.getProductId()).setProductCategoriesToUnassign(List.of(CATEGORY_TO_ASSIGN2));
        ProductDto expected = productToModify.setCategories(List.of(ASSIGNED_CATEGORY));

        productFacade.modifyProduct(modifyProductDto);

        Option<ProductDto> productOpt = productFacade.findProductByProductId(productToModify.getProductId());
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    @ParameterizedTest
    @MethodSource("modifyProductValidationProvider")
    void modifyProductValidationTest(ModifyProductDto dto, String expectedMessage, ErrorType expectedErrorType) {
        Either<AppError, ProductDto> result = productFacade.modifyProduct(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> modifyProductValidationProvider() {
        Arguments negativeAvailablePiecesNumber = Arguments.of(modifyProductDtoWithNegativeAvailablePiecesNumber(),
                                                               "Product pieces number has to be greater than zero.", ErrorType.VALIDATION);
        Arguments zeroAvailablePiecesNumber = Arguments.of(modifyProductDtoWithZeroAvailablePiecesNumber(),
                                                         "Product pieces number has to be greater than zero.", ErrorType.VALIDATION);
        Arguments nullProductId = Arguments.of(modifyProductDtoWithProductId(null),
                                               "Product id cannot be empty.", ErrorType.VALIDATION);
        BigDecimal nonexistentProductId = BigDecimal.valueOf(1000);
        Arguments nonexistentProduct = Arguments.of(modifyProductDtoWithProductId(nonexistentProductId),
                                                    "Product with productId " + nonexistentProductId + " not found", ErrorType.NOT_FOUND);
        String nonexistentCategoryName = "example";
        Arguments nonexistentCategory = Arguments.of(modifyProductDtoWithNonexistentCategory(nonexistentCategoryName),
                                                     "Category with name " + nonexistentCategoryName + " does not exists.", ErrorType.NOT_FOUND);

        return Stream.of(negativeAvailablePiecesNumber,
                         zeroAvailablePiecesNumber,
                         nullProductId,
                         nonexistentProduct,
                         nonexistentCategory
        );
    }

}
