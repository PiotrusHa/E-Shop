package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.BookProductDto;
import piotrusha.e_shop.core.product.domain.dto.CancelProductBookingDto;
import piotrusha.e_shop.core.product.domain.dto.SellProductDto;

import java.math.BigDecimal;

class SampleDtosToValidate {

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
