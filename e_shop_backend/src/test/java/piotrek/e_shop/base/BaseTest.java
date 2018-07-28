package piotrek.e_shop.base;

import org.junit.jupiter.params.provider.Arguments;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.stub.model.Categories;
import piotrek.e_shop.stub.model.Products;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductWith2Categories;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public abstract class BaseTest {

    protected void assertProduct(Product expected, Product actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("product",
                  () -> assertEquals(expected.getName(), actual.getName()),
                  () -> assertEquals(expected.getId(), actual.getId()),
                  () -> assertEquals(expected.getPrice(), actual.getPrice()),
                  () -> assertEquals(expected.getAvailablePiecesNumber(), actual.getAvailablePiecesNumber()),
                  () -> assertEquals(expected.getSoldPiecesNumber(), actual.getSoldPiecesNumber()),
                  () -> assertEquals(expected.getExtraInfo(), actual.getExtraInfo()),
                  () -> assertCategories(expected.getCategories(), actual.getCategories())
        );
    }

    protected void assertProducts(List<Product> expected, List<Product> actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertProduct(expected.get(i), actual.get(i));
        }
    }

    protected void assertCategory(Category expected, Category actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("category",
                  () -> assertEquals(expected.getId(), actual.getId()),
                  () -> assertEquals(expected.getName(), actual.getName())
        );
    }

    protected void assertCategories(List<Category> expected, List<Category> actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertCategory(expected.get(i), actual.get(i));
        }
    }

    /* *********************************  Argument Providers **********************************************************/

    protected static Stream<Arguments> categoryNameProvider() {
        return Stream.of(
                Arguments.of(Categories.TestCategoryToys.NAME, List.of(TestProductWith3Categories.PRODUCT)),
                Arguments.of(Categories.TestCategoryCosmetics.NAME, List.of(TestProductWith2Categories.PRODUCT, TestProductWith3Categories.PRODUCT)),
                Arguments.of(Categories.TestCategoryFood.NAME, List.of(TestProductBread.PRODUCT, TestProductWith2Categories.PRODUCT, TestProductWith3Categories.PRODUCT))
        );
    }

}
