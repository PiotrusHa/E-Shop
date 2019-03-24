package piotrusha.e_shop.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import piotrusha.e_shop.product.domain.dto.ProductDto;

class Assertions {

    static void assertProductDto(ProductDto expected, ProductDto actual) {
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getAvailablePiecesNumber(), actual.getAvailablePiecesNumber());
        assertEquals(expected.getBookedPiecesNumber(), actual.getBookedPiecesNumber());
        assertEquals(expected.getSoldPiecesNumber(), actual.getSoldPiecesNumber());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getCategories()
                             .size(), actual.getCategories()
                                            .size());
        assertTrue(expected.getCategories()
                           .containsAll(actual.getCategories()));
    }

}
