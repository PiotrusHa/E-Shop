package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

class ProductModificationTest extends ProductTest {

    private static final String ASSIGNED_CATEGORY = "beer";
    private static final String ASSIGNED_CATEGORY_2 = "category1";
    private static final String CATEGORY_TO_ASSIGN2 = "category2";

    private ProductDto productToModify;

    @BeforeEach
    protected void init() {
        super.init();

        createCategory(ASSIGNED_CATEGORY);
        createCategory(ASSIGNED_CATEGORY_2);
        createCategory(CATEGORY_TO_ASSIGN2);

        productToModify = createProductWithCategories(List.of(ASSIGNED_CATEGORY, ASSIGNED_CATEGORY_2));
    }

    @ParameterizedTest
    @MethodSource("modifyProductDtoProvider")
    void modifyProduct(Function<BigDecimal, ModifyProductDto> dtoProvider) {
        ModifyProductDto modifyProductDto = dtoProvider.apply(productToModify.getProductId());
        ProductDto expectedChangedProduct = expectedProductDto(productToModify, modifyProductDto);

        Either<AppError, ProductDto> result = productFacade.modifyProduct(modifyProductDto);

        assertTrue(result.isRight());
        assertProductDto(expectedChangedProduct, result.get());
        assertWithProductFromDatabase(productToModify.getProductId(), expectedChangedProduct);
    }

    private static Stream<Function<BigDecimal, ModifyProductDto>> modifyProductDtoProvider() {
        return Stream.of(ProductModificationTest::modifyPriceDto,
                         ProductModificationTest::modifyAvailablePiecesNumberDto,
                         ProductModificationTest::modifyDescriptionDto,
                         ProductModificationTest::assignCategoryDto,
                         ProductModificationTest::unassignCategoryDto,
                         ProductModificationTest::assignAlreadyAssignedCategoryDto,
                         ProductModificationTest::unassignNotAssignedCategoryDto);
    }

    private ProductDto expectedProductDto(ProductDto currentProduct, ModifyProductDto dto) {
        ProductDto.ProductDtoBuilder product = currentProduct.toBuilder();
        if (dto.getProductPrice() != null) {
            product.price(dto.getProductPrice());
        }
        if (dto.getProductAvailablePiecesNumber() != null) {
            product.availablePiecesNumber(dto.getProductAvailablePiecesNumber());
        }
        if (dto.getProductDescription() != null) {
            product.description(dto.getProductDescription());
        }

        Set<String> newCategories = new HashSet<>(currentProduct.getCategories());
        if (dto.getProductCategoriesToAssign() != null) {
            newCategories.addAll(dto.getProductCategoriesToAssign());
        }
        if (dto.getProductCategoriesToUnassign() != null) {
            newCategories.removeAll(dto.getProductCategoriesToUnassign());
        }
        product.categories(newCategories);

        return product.build();
    }

    private static ModifyProductDto modifyPriceDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductPrice(BigDecimal.valueOf(0.99));
    }

    private static ModifyProductDto modifyAvailablePiecesNumberDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductAvailablePiecesNumber(1912);
    }

    private static ModifyProductDto modifyDescriptionDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductDescription("new description");
    }

    private static ModifyProductDto assignCategoryDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductCategoriesToAssign(List.of(CATEGORY_TO_ASSIGN2));
    }

    private static ModifyProductDto unassignCategoryDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductCategoriesToUnassign(List.of(ASSIGNED_CATEGORY_2));
    }

    private static ModifyProductDto assignAlreadyAssignedCategoryDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductCategoriesToAssign(List.of(ASSIGNED_CATEGORY_2));
    }

    private static ModifyProductDto unassignNotAssignedCategoryDto(BigDecimal productId) {
        return modifyProductDto(productId).setProductCategoriesToAssign(List.of(CATEGORY_TO_ASSIGN2));
    }

    private static ModifyProductDto modifyProductDto(BigDecimal productId) {
        return new ModifyProductDto(productId);
    }

}
