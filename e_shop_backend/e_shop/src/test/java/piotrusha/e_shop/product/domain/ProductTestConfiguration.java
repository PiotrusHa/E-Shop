package piotrusha.e_shop.product.domain;

public class ProductTestConfiguration {

    public static ProductFacade productFacade() {
        return new ProductConfiguration().productFacade();
    }

}
