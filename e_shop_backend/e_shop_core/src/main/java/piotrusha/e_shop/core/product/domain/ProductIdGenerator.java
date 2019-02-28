package piotrusha.e_shop.core.product.domain;

import java.math.BigDecimal;

class ProductIdGenerator {

    private final ProductRepository productRepository;

    ProductIdGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    BigDecimal generate() {
        BigDecimal maxProductId = productRepository.findMaxProductId().orElse(BigDecimal.ZERO);
        return maxProductId.add(BigDecimal.ONE);
    }

}