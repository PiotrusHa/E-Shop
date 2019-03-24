package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;

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

        return new Product().setProductId(generatedId)
                            .setName(dto.getProductName())
                            .setPrice(dto.getPrice())
                            .setAvailablePiecesNumber(dto.getAvailablePiecesNumber())
                            .setBookedPiecesNumber(0)
                            .setSoldPiecesNumber(0)
                            .setDescription(dto.getDescription())
                            .setCategories(categories);
    }

}
