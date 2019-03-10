package piotrusha.e_shop.core.product.domain;

public class ProductTestConfiguration {

    public static ProductFacade productFacade() {
        return new ProductConfiguration().productFacade();
    }

}
