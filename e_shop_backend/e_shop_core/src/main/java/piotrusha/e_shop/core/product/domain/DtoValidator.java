package piotrusha.e_shop.core.product.domain;

import com.google.common.base.Strings;
import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.core.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.core.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;
import piotrusha.e_shop.core.product.domain.exception.CategoryValidationException;
import piotrusha.e_shop.core.product.domain.exception.ProductValidationException;

import java.math.BigDecimal;
import java.util.List;

class DtoValidator {

    private final CategoryRepository categoryRepository;

    DtoValidator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    void validateDto(CreateProductCategoryDto dto) {
        if (Strings.isNullOrEmpty(dto.getCategoryName())) {
            throw CategoryValidationException.emptyCategoryName();
        }
        if (categoryRepository.existsByName(dto.getCategoryName())) {
            throw CategoryValidationException.categoryNameAlreadyExists(dto.getCategoryName());
        }
    }

    void validateDto(CreateProductDto dto) {
        validateProductName(dto.getProductName());
        validateProductPrice(dto.getPrice());
        validateAvailablePiecesNumber(dto.getAvailablePiecesNumber());
        validateCategories(dto.getCategories());
    }

    void validateDto(ModifyProductDto dto) {
        validateProductId(dto.getProductId());
        if (dto.getProductAvailablePiecesNumber() != null) {
            validateAvailablePiecesNumber(dto.getProductAvailablePiecesNumber());
        }
        if (dto.getProductCategoriesToAssign() != null && !dto.getProductCategoriesToAssign()
                                                              .isEmpty()) {
            validateCategories(dto.getProductCategoriesToAssign());
        }
    }

    void validateDto(BookProductDto dto) {
        validateProductId(dto.getProductId());
        validateBookPiecesNumber(dto.getPiecesNumber());
    }

    void validateDto(CancelProductBookingDto dto) {
        validateProductId(dto.getProductId());
        validateBookPiecesNumber(dto.getPiecesNumber());
    }

    void validateDto(SellProductDto dto) {
        validateProductId(dto.getProductId());
        validateBookPiecesNumber(dto.getPiecesNumber());
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

    private void validateProductId(BigDecimal productId) {
        if (productId == null) {
            throw ProductValidationException.emptyProductId();
        }
    }

    private void validateBookPiecesNumber(Integer piecesNumber) {
        if (piecesNumber == null || piecesNumber <= 0) {
            throw ProductValidationException.wrongPiecesNumber();
        }
    }

}
