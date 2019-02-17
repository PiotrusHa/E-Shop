package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

class ProductModifier {

    private final ProductValidator productValidator;
    private final ProductRepository productRepository;

    ProductModifier(ProductRepository productRepository, ProductValidator productValidator) {
        this.productValidator = productValidator;
        this.productRepository = productRepository;
    }

    void modifyProduct(ModifyProductDto dto) {
        validate(dto);
        Product product = get(dto.getProductId());
        modify(product, dto);
        save(product);
    }

    private void validate(ModifyProductDto dto) {
        productValidator.validate(dto);
    }

    private Product get(BigDecimal productId) {
        return productRepository.findByProductId(productId)
                                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    private void modify(Product product, ModifyProductDto dto) {
        if (dto.getProductPrice() != null) {
            product.setPrice(dto.getProductPrice());
        }
        if (dto.getProductAvailablePiecesNumber() != null) {
            product.setAvailablePiecesNumber(dto.getProductAvailablePiecesNumber());
        }
        if (dto.getProductDescription() != null) {
            product.setDescription(dto.getProductDescription());
        }
        if (dto.getProductCategoriesToAssign() != null && !dto.getProductCategoriesToAssign()
                                                              .isEmpty()) {
            product.assingCategories(convertToCategories(dto.getProductCategoriesToAssign()));
        }
        if (dto.getProductCategoriesToUnassign() != null && !dto.getProductCategoriesToUnassign()
                                                                .isEmpty()) {
            product.unassignCategories(convertToCategories(dto.getProductCategoriesToUnassign()));
        }
    }

    private List<Category> convertToCategories(List<String> categories) {
        return categories.stream()
                         .map(Category::new)
                         .collect(Collectors.toList());
    }

    private void save(Product product) {
        productRepository.save(product);
    }

}
