package piotrusha.e_shop.core.product.domain;

import piotrusha.e_shop.core.product.domain.dto.BookProductDto;

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

}
