package piotrek.e_shop.base;

import org.junit.jupiter.api.BeforeAll;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.repositories.CategoryRepository;
import piotrek.e_shop.api.repositories.ProductRepository;
import piotrek.e_shop.api.services.CategoryService;
import piotrek.e_shop.api.services.ProductService;
import piotrek.e_shop.core.services.CategoryServiceImpl;
import piotrek.e_shop.core.services.ProductServiceImpl;

public abstract class BaseServiceTest extends BaseTestWithDatabase {

    @Mock
    protected ProductRepository productRepositoryMock;
    @Mock
    protected CategoryRepository categoryRepositoryMock;

    @InjectMocks
    protected CategoryServiceImpl categoryServiceDbSaveMock;

    protected ProductServiceImpl productServiceDbSaveMock;    // didn't use @InjectMocks because it use not mocked CategoryService

    @Autowired
    protected ProductService productService;
    @Autowired
    protected CategoryService categoryService;

    @BeforeAll
    void init() {
        MockitoAnnotations.initMocks(this);
        super.init();

        productServiceDbSaveMock = new ProductServiceImpl(productRepositoryMock, categoryService);
    }

}
