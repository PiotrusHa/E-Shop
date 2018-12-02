package piotrusha.e_shop.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import piotrusha.e_shop.EShopTestConfiguration;
import piotrusha.e_shop.core.repository.BillRepository;
import piotrusha.e_shop.core.repository.CategoryRepository;
import piotrusha.e_shop.core.repository.ProductRepository;
import piotrusha.e_shop.core.repository.PurchaseProductRepository;

import static piotrusha.e_shop.stub.model.Bills.TEST_BILLS;
import static piotrusha.e_shop.stub.model.Categories.TEST_CATEGORIES;
import static piotrusha.e_shop.stub.model.Products.TEST_PRODUCTS;
import static piotrusha.e_shop.stub.model.PurchaseProducts.TEST_PURCHASE_PRODUCTS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
})
@ContextConfiguration(classes = EShopTestConfiguration.class)
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
