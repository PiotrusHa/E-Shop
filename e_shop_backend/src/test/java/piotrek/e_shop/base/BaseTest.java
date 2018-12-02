package piotrek.e_shop.base;

import org.junit.jupiter.params.provider.Arguments;
import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.ProductBuilder;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.stub.model.Categories;
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
                  () -> assertEquals(expected.getId(), actual.getId()),
                  () -> assertProductWithoutId(expected, actual)
        );
    }

    protected void assertProductWithoutId(Product expected, Product actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("product",
                  () -> assertEquals(expected.getName(), actual.getName()),
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
                  () -> assertCategoryWithoutId(expected, actual)
        );
    }

    protected void assertCategoryWithoutId(Category expected, Category actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("category",
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

    protected void assertPurchaseProduct(PurchaseProduct expected, PurchaseProduct actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("purchaseProduct",
                  () -> assertEquals(expected.getId(), actual.getId()),
                  () -> assertPurchaseProductWithoutId(expected, actual)
        );
    }

    protected void assertPurchaseProductWithoutId(PurchaseProduct expected, PurchaseProduct actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("purchaseProduct",
                  () -> assertProduct(expected.getProduct(), actual.getProduct()),
                  () -> assertEquals(expected.getPiecesNumber(), actual.getPiecesNumber()),
                  () -> assertEquals(expected.getPiecePrice(), actual.getPiecePrice())
        );
    }

    protected void assertPurchaseProducts(List<PurchaseProduct> expected, List<PurchaseProduct> actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertPurchaseProduct(expected.get(i), actual.get(i));
        }
    }

    protected void assertPurchaseProductsWithoutId(List<PurchaseProduct> expected, List<PurchaseProduct> actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertPurchaseProductWithoutId(expected.get(i), actual.get(i));
        }
    }

    protected void assertBill(Bill expected, Bill actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("bill",
                  () -> assertEquals(expected.getId(), actual.getId()),
                  () -> assertBillWithoutId(expected, actual)
        );
    }

    protected void assertBillWithoutId(Bill expected, Bill actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertAll("bill",
                  () -> assertEquals(expected.getClientId(), actual.getClientId()),
                  () -> assertEquals(expected.getState(), actual.getState()),
                  () -> assertEquals(expected.getPriceSum(), actual.getPriceSum()),
                  () -> assertPurchaseProductsWithoutId(expected.getPurchaseProducts(), actual.getPurchaseProducts())
        );
    }

    protected void assertBills(List<Bill> expected, List<Bill> actual) {
        assertNotNull(expected);
        assertNotNull(actual);

        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertBill(expected.get(i), actual.get(i));
        }
    }

    /* *********************************  Creation methods *******************************************************/

    protected PurchaseProduct createPurchaseProductWithUpdatedProduct(Product product, int soldPiecesNumber) {
        return new PurchaseProductBuilder(new ProductBuilder(product).id(product.getId())
                                                                     .availablePiecesNumber(product.getAvailablePiecesNumber() - soldPiecesNumber)
                                                                     .soldPiecesNumber(product.getSoldPiecesNumber() + soldPiecesNumber)
                                                                     .build(),
                                          soldPiecesNumber).build();
    }

    protected PurchaseProduct createPurchaseProductToCancel(Product product, int soldPiecesNumber) {
        return new PurchaseProductBuilder(new ProductBuilder(product).id(product.getId())
                                                                     .availablePiecesNumber(product.getAvailablePiecesNumber() + soldPiecesNumber)
                                                                     .soldPiecesNumber(product.getSoldPiecesNumber() - soldPiecesNumber)
                                                                     .build(),
                                          soldPiecesNumber).build();
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
