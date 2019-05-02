package piotrusha.e_shop.product.domain;

import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.product.domain.dto.CreateProductCategoryDto;
import piotrusha.e_shop.product.domain.dto.CreateProductDto;
import piotrusha.e_shop.product.domain.dto.ModifyProductDto;
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.math.BigDecimal;
import java.util.List;

class SampleDtosToValidate {

    static CreateProductCategoryDto createProductCategoryDtoWithEmptyName() {
        return new CreateProductCategoryDto("");
    }

    static CreateProductCategoryDto createProductCategoryDtoWithNullName() {
        return new CreateProductCategoryDto(null);
    }

    static CreateProductDto createProductDtoWithoutName() {
        return new CreateProductDto("", BigDecimal.ONE, 1, "", List.of());
    }

    static CreateProductDto createProductDtoWithoutPrice() {
        return new CreateProductDto("Tatra", null, 1, "", List.of());
    }

    static CreateProductDto createProductDtoWithoutAvailablePiecesNumber() {
        return new CreateProductDto("Tatra", BigDecimal.TEN, null, "", List.of());
    }

    static CreateProductDto createProductDtoWithNegativeAvailablePiecesNumber() {
        return new CreateProductDto("Tatra", BigDecimal.TEN, -10, "", List.of());
    }

    static CreateProductDto createProductDtoWithZeroAvailablePiecesNumber() {
        return new CreateProductDto("Tatra", BigDecimal.TEN, 0, "", List.of());
    }

    static CreateProductDto createProductDtoWithNonexistentCategory(String categoryName) {
        return new CreateProductDto("Tatra", BigDecimal.TEN, 5, "", List.of(categoryName));
    }

    static ModifyProductDto modifyProductDtoWithNegativeAvailablePiecesNumber() {
        return new ModifyProductDto(BigDecimal.ONE).setProductAvailablePiecesNumber(-100);
    }

    static ModifyProductDto modifyProductDtoWithZeroAvailablePiecesNumber() {
        return new ModifyProductDto(BigDecimal.ONE).setProductAvailablePiecesNumber(0);
    }

    static ModifyProductDto modifyProductDtoWithProductId(BigDecimal nonexistentProductId) {
        return new ModifyProductDto(nonexistentProductId);
    }

    static ModifyProductDto modifyProductDtoWithNonexistentCategory(String categoryName) {
        return new ModifyProductDto(BigDecimal.ONE).setProductCategoriesToAssign(List.of(categoryName));
    }

    static BookProductDto bookProductDtoWithoutProductId() {
        return new BookProductDto(null, 1);
    }

    static BookProductDto bookProductDtoWithProductId(BigDecimal productId) {
        return new BookProductDto(productId, 1);
    }

    static BookProductDto bookProductDtoWithoutPiecesNumber() {
        return new BookProductDto(BigDecimal.ONE, null);
    }

    static BookProductDto bookProductDtoWithNegativePiecesNumber() {
        return new BookProductDto(BigDecimal.ONE, -2);
    }

    static BookProductDto bookProductDtoWithZeroPiecesNumber() {
        return new BookProductDto(BigDecimal.ONE, 0);
    }

    static CancelProductBookingDto cancelProductBookingDtoWithoutProductId() {
        return new CancelProductBookingDto(null, 1);
    }

    static CancelProductBookingDto cancelProductBookingDtoWithProductId(BigDecimal productId) {
        return new CancelProductBookingDto(productId, 1);
    }

    static CancelProductBookingDto cancelProductBookingDtoWithoutPiecesNumber() {
        return new CancelProductBookingDto(BigDecimal.ONE, null);
    }

    static CancelProductBookingDto cancelProductBookingDtoWithNegativePiecesNumber() {
        return new CancelProductBookingDto(BigDecimal.ONE, -2);
    }

    static CancelProductBookingDto cancelProductBookingDtoWithZeroPiecesNumber() {
        return new CancelProductBookingDto(BigDecimal.ONE, 0);
    }

    static SellProductDto sellProductDtoWithoutProductId() {
        return new SellProductDto(null, 1);
    }

    static SellProductDto sellProductDtoWithProductId(BigDecimal productId) {
        return new SellProductDto(productId, 1);
    }

    static SellProductDto sellProductDtoWithoutPiecesNumber() {
        return new SellProductDto(BigDecimal.ONE, null);
    }

    static SellProductDto sellProductDtoWithNegativePiecesNumber() {
        return new SellProductDto(BigDecimal.ONE, -2);
    }

    static SellProductDto sellProductDtoWithZeroPiecesNumber() {
        return new SellProductDto(BigDecimal.ONE, 0);
    }

}
