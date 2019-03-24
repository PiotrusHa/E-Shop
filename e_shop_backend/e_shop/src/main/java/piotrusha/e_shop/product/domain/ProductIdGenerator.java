package piotrusha.e_shop.product.domain;

import java.math.BigDecimal;

class ProductIdGenerator {

    private final ProductRepository productRepository;

    ProductIdGenerator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    BigDecimal generate() {
        BigDecimal maxProductId = productRepository.findMaxProductId().getOrElse(BigDecimal.ZERO);
        return maxProductId.add(BigDecimal.ONE);
    }

}
