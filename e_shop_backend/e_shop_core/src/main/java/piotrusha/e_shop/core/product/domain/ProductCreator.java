package piotrusha.e_shop.core.product.domain;

import com.google.common.base.Strings;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductCreator {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductIdGenerator productIdGenerator;
    private final ProductConverter productConverter;

    ProductCreator(ProductRepository productRepository, CategoryRepository categoryRepository, ProductIdGenerator productIdGenerator,
                   ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productIdGenerator = productIdGenerator;
        this.productConverter = productConverter;
    }

    ProductDto createProduct(CreateProductDto dto) {
        validate(dto);
        Product product = create(dto);
        save(product);
        return productConverter.toDto(product);
    }

    private void validate(CreateProductDto dto) {
        if (Strings.isNullOrEmpty(dto.getProductName())) {
            throw ProductValidationException.emptyName();
        }
        if (dto.getPrice() == null) {
            throw ProductValidationException.emptyPrice();
        }
        if (dto.getAvailablePiecesNumber() == null || dto.getAvailablePiecesNumber() < 0) {
            throw ProductValidationException.wrongAvailablePiecesNumber();
        }
        for (String categoryName : dto.getCategories()) {
            if (!categoryRepository.existsByName(categoryName)) {
                throw ProductValidationException.categoryDoesNotExists(categoryName);
            }
        }
    }

    private Product create(CreateProductDto dto) {
        BigDecimal generatedId = productIdGenerator.generate();
        List<Category> categories = dto.getCategories()
                                       .stream()
                                       .map(Category::new)
                                       .collect(Collectors.toList());

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
