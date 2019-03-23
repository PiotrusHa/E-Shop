package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ProductFinder {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    ProductFinder(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    Optional<ProductDto> findByProductId(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .map(productConverter::toDto);
    }

    List<ProductDto> findProductsByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName)
                                .stream()
                                .map(productConverter::toDto)
                                .collect(Collectors.toList());
    }

}
