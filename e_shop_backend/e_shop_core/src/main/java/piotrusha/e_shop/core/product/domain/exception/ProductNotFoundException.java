package piotrusha.e_shop.core.product.domain.exception;

import java.math.BigDecimal;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(BigDecimal productId) {
        super(String.format("Product with productId %s not found", productId));
    }

}
