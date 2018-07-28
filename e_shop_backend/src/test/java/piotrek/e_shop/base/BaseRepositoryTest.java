package piotrek.e_shop.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import piotrek.e_shop.api.repositories.CategoryRepository;
import piotrek.e_shop.api.repositories.ProductRepository;

import static piotrek.e_shop.stub.model.Categories.TEST_CATEGORIES;
import static piotrek.e_shop.stub.model.Products.TEST_PRODUCTS;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
public abstract class BaseRepositoryTest extends BaseTest {

    @Autowired
    protected ProductRepository productRepository;
    @Autowired
    protected CategoryRepository categoryRepository;

    @BeforeAll
    void init() {
        categoryRepository.saveAll(TEST_CATEGORIES);
        productRepository.saveAll(TEST_PRODUCTS);
    }

}
