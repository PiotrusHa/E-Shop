package piotrek.e_shop.core.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.exceptions.EntityNotFoundException;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.base.BaseTestWithDatabase;
import piotrek.e_shop.model.Category;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.ProductBuilder;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.stub.model.Categories;
import piotrek.e_shop.stub.model.Products;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductWith3Categories;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Product Service Test")
class ProductServiceTest extends BaseTestWithDatabase {

    @Autowired
    protected ProductService productService;

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

        result.sort(Comparator.comparing(Product::getId));
        assertProducts(expectedResults, result);
    }

    @Test
    void findAll() {
        List<Product> expectedResult = Products.TEST_PRODUCTS;

        List<Product> result = productService.findAll();

        assertProducts(expectedResult, result);
    }

    @Test
    void save() {
        Product productToSave = new ProductBuilder(TestProductBread.PRODUCT).build();

        Product result = productService.add(productToSave);

        assertProductWithoutId(productToSave, result);
        assertNotNull(result.getId());
    }

    @Test
    void saveAll() {
        List<Product> productsToSave = List.of(new ProductBuilder(TestProductBread.PRODUCT).build(),
                                               new ProductBuilder(TestProductWith3Categories.PRODUCT).build());

        List<Product> result = productService.addAll(productsToSave);

        assertEquals(productsToSave.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertProductWithoutId(productsToSave.get(i), result.get(i));
            assertNotNull(result.get(i).getId());
        }
    }

    @Test
    void assignCategoryToProduct() {
        Product existentProduct = new ProductBuilder(TestProductBread.PRODUCT).id(TestProductBread.ID).build();
        List<Category> categories = new ArrayList<>(existentProduct.getCategories());
        categories.add(Categories.TestCategoryToys.CATEGORY);
        existentProduct.setCategories(categories);

        Product result = productService.update(existentProduct);

        assertProduct(existentProduct, result);
    }

    @Test
    void unassignCategoryFromProduct() {
        Product existentProduct = new ProductBuilder(TestProductBread.PRODUCT).id(TestProductBread.ID).build();
        List<Category> categories = new ArrayList<>(existentProduct.getCategories());
        categories.remove(0);
        existentProduct.setCategories(categories);

        Product result = productService.update(existentProduct);

        assertProduct(existentProduct, result);
    }

    @Test
    void updateWithNonexistentIdThrowEntityNotFoundException() {
        BigDecimal nonExistentId = BigDecimal.valueOf(1000);
        Product productToSave = new ProductBuilder(TestProductBread.PRODUCT).id(nonExistentId).build();

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.update(productToSave));

        assertEquals(Product.class, exception.getResourceClass());
        assertEquals(nonExistentId, exception.getResourceId());
    }

    @Test
    void updateWithIdNullThrowEntityNotFoundException() {
        Product productToSave = new ProductBuilder(TestProductBread.PRODUCT).build();

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> productService.update(productToSave));

        assertEquals(Product.class, exception.getResourceClass());
        assertNull(exception.getResourceId());
    }

}
