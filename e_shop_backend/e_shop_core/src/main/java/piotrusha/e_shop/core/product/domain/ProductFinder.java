package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Optional;

class ProductFinder {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    ProductFinder(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    Optional<ProductDto> findByProductId(BigDecimal productId) {
        return productRepository.findById(productId)
                                .map(productConverter::toDto);
    }

}
