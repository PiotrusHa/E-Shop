package piotrusha.e_shop.core.product.domain;

import com.google.common.base.Strings;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;

class ProductValidator {

    private final CategoryRepository categoryRepository;

    ProductValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void validate(CreateProductDto dto) {
        validateProductName(dto.getProductName());
        validateProductPrice(dto.getPrice());
        validateAvailablePiecesNumber(dto.getAvailablePiecesNumber());
        validateCategories(dto.getCategories());
    }

    private void validateProductName(String productName) {
        if (Strings.isNullOrEmpty(productName)) {
            throw ProductValidationException.emptyName();
        }
    }

    private void validateProductPrice(BigDecimal productPrice) {
        if (productPrice == null) {
            throw ProductValidationException.emptyPrice();
        }
    }

    private void validateAvailablePiecesNumber(Integer availablePiecesNumber) {
        if (availablePiecesNumber == null || availablePiecesNumber < 0) {
            throw ProductValidationException.wrongAvailablePiecesNumber();
        }
    }

    private void validateCategories(List<String> categories) {
        for (String categoryName : categories) {
            if (!categoryRepository.existsByName(categoryName)) {
                throw ProductValidationException.categoryDoesNotExists(categoryName);
            }
        }
    }

    void validate(ModifyProductDto dto) {
        validateProductId(dto.getProductId());
        if (dto.getProductAvailablePiecesNumber() != null) {
            validateAvailablePiecesNumber(dto.getProductAvailablePiecesNumber());
        }
        if (dto.getProductCategoriesToAssign() != null && !dto.getProductCategoriesToAssign().isEmpty()) {
            validateCategories(dto.getProductCategoriesToAssign());
        }
    }

    private void validateProductId(BigDecimal productId) {
        if (productId == null) {
            throw ProductValidationException.emptyProductId();
        }
    }

}
