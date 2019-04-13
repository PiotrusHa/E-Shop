package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Option;
import org.junit.jupiter.api.BeforeEach;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

public class ProductTest {

    protected ProductFacade productFacade;

    @BeforeEach
    protected void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

    void createCategory(String name) {
        CreateProductCategoryDto dto  = new CreateProductCategoryDto(name);
        productFacade.createProductCategory(dto);
    }

    ProductDto createProduct() {
        String categoryName = "Example category";
        createCategory(categoryName);
        CreateProductDto createProductDto = new CreateProductDto("Example product",
                                                                 BigDecimal.valueOf(1.98),
                                                                 997,
                                                                 "description",
                                                                 List.of(categoryName));
        return createProduct(createProductDto);
    }

    ProductDto createProduct2() {
        String categoryName = "Another category";
        createCategory(categoryName);
        CreateProductDto createProductDto = new CreateProductDto("Another product",
                                                                 BigDecimal.TEN,
                                                                 111,
                                                                 "description",
                                                                 List.of(categoryName));
        return createProduct(createProductDto);
    }

    ProductDto createProductWithCategories(List<String> categories) {
        CreateProductDto createProductDto = new CreateProductDto("Tatra",
                                                                 BigDecimal.TEN,
                                                                 123,
                                                                 "description", categories);
        return createProduct(createProductDto);
    }

    private ProductDto createProduct(CreateProductDto createProductDto) {
        return productFacade.createProduct(createProductDto).get();
    }

    ProductDto createBookedProduct() {
        ProductDto productDto = createProduct();
        BookProductDto bookProductDto = new BookProductDto(productDto.getProductId(), 400);
        return createBookedProduct(bookProductDto);
    }

    ProductDto createBookedProduct2() {
        ProductDto productDto = createProduct2();
        BookProductDto bookProductDto = new BookProductDto(productDto.getProductId(), 50);
        return createBookedProduct(bookProductDto);
    }

    private ProductDto createBookedProduct(BookProductDto bookProductDto) {
        return productFacade.bookProducts(List.of(bookProductDto)).get().get(0);
    }

    void assertWithProductFromDatabase(BigDecimal productId, ProductDto expected) {
        Option<ProductDto> productOpt = productFacade.findProductByProductId(productId);
        assertTrue(productOpt.isDefined());
        assertProductDto(expected, productOpt.get());
    }

    void assertProductDidNotChange(ProductDto productDto) {
        assertWithProductFromDatabase(productDto.getProductId(), productDto);
    }

    static void assertProductDto(ProductDto expected, ProductDto actual) {
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getAvailablePiecesNumber(), actual.getAvailablePiecesNumber());
        assertEquals(expected.getBookedPiecesNumber(), actual.getBookedPiecesNumber());
        assertEquals(expected.getSoldPiecesNumber(), actual.getSoldPiecesNumber());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategories()
                             .size(), actual.getCategories()
                                            .size());
        assertTrue(expected.getCategories()
                           .containsAll(actual.getCategories()));
    }

}
