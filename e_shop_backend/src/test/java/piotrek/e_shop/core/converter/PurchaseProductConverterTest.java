package piotrek.e_shop.core.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.exceptions.EntityNotFoundException;
import piotrek.e_shop.base.BaseTestWithDatabase;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.model.dto.PurchaseProductDto;
import piotrek.e_shop.stub.model.Products.TestProductBeer;
import piotrek.e_shop.stub.model.Products.TestProductBread;

import java.math.BigDecimal;
import java.util.List;

class PurchaseProductConverterTest extends BaseTestWithDatabase {

    @Autowired
    private PurchaseProductConverter converter;

    @Test
    void convertToDatabaseObjects() {
        List<PurchaseProductDto> dtos = List.of(new PurchaseProductDto(TestProductBeer.ID, 4),
                                                new PurchaseProductDto(TestProductBread.ID, 1));
        List<PurchaseProduct> expectedPurchaseProducts = List.of(new PurchaseProductBuilder(TestProductBeer.PRODUCT, 4).build(),
                                                                 new PurchaseProductBuilder(TestProductBread.PRODUCT, 1).build());

        List<PurchaseProduct> purchaseProducts = converter.convertToDatabaseObjects(dtos);

        assertPurchaseProducts(expectedPurchaseProducts, purchaseProducts);
    }

    @Test
    void convertToDatabaseObjectsThrowEntityNotFoundException() {
        BigDecimal nonexistentId = BigDecimal.valueOf(997);
        List<PurchaseProductDto> dtos = List.of(new PurchaseProductDto(TestProductBeer.ID, 4),
                                                new PurchaseProductDto(nonexistentId, 1));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> converter.convertToDatabaseObjects(dtos));

        assertEquals(Product.class, exception.getResourceClass());
        assertEquals(nonexistentId, exception.getResourceId());
    }

}