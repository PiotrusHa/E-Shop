package piotrusha.e_shop.product.domain;

import org.junit.jupiter.api.BeforeEach;

public class ProductTest {

    protected ProductFacade productFacade;

    @BeforeEach
    protected void init() {
        productFacade = new ProductConfiguration().productFacade();
    }

}
