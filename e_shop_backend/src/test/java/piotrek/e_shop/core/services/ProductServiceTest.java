package piotrek.e_shop.core.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import piotrek.e_shop.base.BaseServiceTest;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DisplayName("Product Service Test")
class ProductServiceTest extends BaseServiceTest {

    @Test
    void findById() {
        Product expectedProduct = TestProductBread.PRODUCT;
        BigDecimal id = expectedProduct.getId();

        Optional<Product> result = productService.findById(id);

        assertTrue(result.isPresent());
        assertProduct(expectedProduct, result.get());
    }

    @Test
    void findByIdReturnOptionalEmpty() {
        BigDecimal id = BigDecimal.valueOf(1410);

        Optional<Product> result = productService.findById(id);

        assertFalse(result.isPresent());
    }

    @Test
    void findByName() {
        List<Product> expectedProducts = List.of(TestProductBread.PRODUCT);
        String name = TestProductBread.NAME;

        List<Product> result = productService.findByName(name);

        assertProducts(expectedProducts, result);
    }

    @ParameterizedTest(name = "CategoryName: {0}")
    @MethodSource("categoryNameProvider")
    void findByCategoryName(String categoryName, List<Product> expectedResults) {
        List<Product> result = productService.findByCategoryName(categoryName);

        assertProducts(expectedResults, result);
    }

    @Test
    void save() {
        Product productToSave = TestProductBread.PRODUCT;

        when(productRepositoryMock.save(productToSave))
                .thenReturn(productToSave);

        Product result = productServiceDbSaveMock.add(productToSave);

        assertProduct(productToSave, result);
    }

    @Test
    void saveAll() {
        List<Product> productsToSave = List.of(TestProductBread.PRODUCT, TestProductWith3Categories.PRODUCT);

        when(productRepositoryMock.saveAll(productsToSave))
                .thenReturn(productsToSave);

        List<Product> result = productServiceDbSaveMock.addAll(productsToSave);

        assertProducts(productsToSave, result);
    }

}
