package piotrusha.e_shop.core.product.domain;

import io.vavr.control.Option;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;

class ProductFinder {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    ProductFinder(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    Option<ProductDto> findByProductId(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .map(productConverter::toDto);
    }

}
