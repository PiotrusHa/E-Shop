package piotrusha.e_shop.core.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.BaseTestWithDatabase;
import piotrusha.e_shop.core.model.Product;

import java.util.Comparator;
import java.util.List;

@DisplayName("Product Repository Test")
class ProductRepositoryTest extends BaseTestWithDatabase {

    @ParameterizedTest(name = "CategoryName: {0}")
    @MethodSource("categoryNameProvider")
    void findProductsByCategoryName(String name, List<Product> expectedResults) {
        List<Product> result = productRepository.findByCategoryName(name);

        result.sort(Comparator.comparing(Product::getId));
        assertProducts(expectedResults, result);
    }

}