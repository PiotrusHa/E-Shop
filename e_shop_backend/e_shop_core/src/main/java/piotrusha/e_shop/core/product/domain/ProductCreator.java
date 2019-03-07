package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

class ProductCreator {

    private final ProductRepository productRepository;
    private final ProductIdGenerator productIdGenerator;
    private final ProductConverter productConverter;

    ProductCreator(ProductRepository productRepository, ProductIdGenerator productIdGenerator,
                   ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productIdGenerator = productIdGenerator;
        this.productConverter = productConverter;
    }

    ProductDto createProduct(CreateProductDto dto) {
        Product product = create(dto);
        save(product);
        return productConverter.toDto(product);
    }

    private Product create(CreateProductDto dto) {
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

    private void save(Product product) {
        productRepository.save(product);
    }

}
