package piotrek.e_shop.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import piotrek.e_shop.api.repositories.BillRepository;
import piotrek.e_shop.api.repositories.CategoryRepository;
import piotrek.e_shop.api.repositories.ProductRepository;
import piotrek.e_shop.api.repositories.PurchaseProductRepository;

import static piotrek.e_shop.stub.model.Bills.TEST_BILLS;
import static piotrek.e_shop.stub.model.Categories.TEST_CATEGORIES;
import static piotrek.e_shop.stub.model.Products.TEST_PRODUCTS;
import static piotrek.e_shop.stub.model.PurchaseProducts.TEST_PURCHASE_PRODUCTS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
})
public abstract class BaseTestWithDatabase extends BaseTest {

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;
    @Autowired
    protected PurchaseProductRepository purchaseProductRepository;
    @Autowired
    protected BillRepository billRepository;

    @BeforeAll
    void init() {
        categoryRepository.saveAll(TEST_CATEGORIES);
        productRepository.saveAll(TEST_PRODUCTS);
        purchaseProductRepository.saveAll(TEST_PURCHASE_PRODUCTS);
        billRepository.saveAll(TEST_BILLS);
    }

}
