package piotrusha.e_shop.product.domain;

import piotrusha.e_shop.product.domain.dto.CreateProductDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

class ProductCreator {

    private final ProductIdGenerator productIdGenerator;

    ProductCreator(ProductIdGenerator productIdGenerator) {
        this.productIdGenerator = productIdGenerator;
    }

    Product createProduct(CreateProductDto dto) {
        BigDecimal generatedId = productIdGenerator.generate();
        Set<Category> categories = dto.getCategories()
                                      .stream()
                                      .map(Category::new)
                                      .collect(Collectors.toSet());

        return Product.builder()
                      .productId(generatedId)
                      .name(dto.getProductName())
                      .price(dto.getPrice())
                      .availablePiecesNumber(dto.getAvailablePiecesNumber())
                      .bookedPiecesNumber(0)
                      .soldPiecesNumber(0)
                      .description(dto.getDescription())
                      .categories(categories)
                      .build();
    }

}
