package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.product.domain.Assertions.assertProductDto;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

class ProductCreationTest extends ProductTest {

    @Test
    void create() {
        CreateProductDto createProductDto = prepareCreateProductDto();
        ProductDto expected = expectedProductDto(createProductDto);

        Either<AppError, ProductDto> createdProduct = productFacade.createProduct(createProductDto);

        assertTrue(createdProduct.isRight());
        assertProductDto(expected, createdProduct.get());
        assertWithProductFromDatabase(expected.getProductId(), expected);
    }

    private CreateProductDto prepareCreateProductDto() {
        String categoryName = "beer";
        productFacade.createProductCategory(new CreateProductCategoryDto(categoryName));

        return new CreateProductDto("Tatra", BigDecimal.ONE, 10, "description", List.of(categoryName));
    }

    private ProductDto expectedProductDto(CreateProductDto createDto) {
        return new ProductDto().setProductId(BigDecimal.ONE)
                               .setName(createDto.getProductName())
                               .setPrice(createDto.getPrice())
                               .setAvailablePiecesNumber(createDto.getAvailablePiecesNumber())
                               .setBookedPiecesNumber(0)
                               .setSoldPiecesNumber(0)
                               .setDescription(createDto.getDescription())
                               .setCategories(new HashSet<>(createDto.getCategories()));
    }

}
