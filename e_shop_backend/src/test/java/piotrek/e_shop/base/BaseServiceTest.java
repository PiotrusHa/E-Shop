package piotrek.e_shop.base;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import piotrek.e_shop.api.repositories.CategoryRepository;
import piotrek.e_shop.api.repositories.ProductRepository;
import piotrek.e_shop.api.services.CategoryService;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.core.services.CategoryServiceImpl;
import piotrek.e_shop.core.services.ProductServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class BaseServiceTest extends BaseTest {

    @Mock
    protected ProductRepository productRepository;
    @Mock
    protected CategoryRepository categoryRepository;

    @InjectMocks
    protected ProductServiceImpl productService;
    @InjectMocks
    protected CategoryServiceImpl categoryService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
    }


}
