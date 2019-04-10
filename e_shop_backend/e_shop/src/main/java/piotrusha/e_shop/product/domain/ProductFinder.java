package piotrusha.e_shop.product.domain;

import io.vavr.control.Option;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

class ProductFinder {

    private final ProductRepository productRepository;

    ProductFinder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    Option<ProductDto> findByProductId(BigDecimal productId) {
        return productRepository.findByProductId(productId);
    }

    List<ProductDto> findProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

}
