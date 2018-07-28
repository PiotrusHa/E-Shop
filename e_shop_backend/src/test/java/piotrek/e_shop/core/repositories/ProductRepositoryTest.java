package piotrek.e_shop.core.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import piotrek.e_shop.base.BaseRepositoryTest;
import piotrek.e_shop.model.Product;

import java.util.List;

@DisplayName("Product Repository Test")
class ProductRepositoryTest extends BaseRepositoryTest {

    @ParameterizedTest(name = "CategoryName: {0}")
    @MethodSource("categoryNameProvider")
    void findProductsByCategoryName(String name, List<Product> expectedResults) {
        List<Product> result = productRepository.findByCategoryName(name);

        assertProducts(expectedResults, result);
    }

}