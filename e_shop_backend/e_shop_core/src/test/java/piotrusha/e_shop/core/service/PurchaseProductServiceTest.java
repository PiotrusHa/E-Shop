package piotrusha.e_shop.core.service;

import static piotrusha.e_shop.stub.model.Products.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import piotrusha.e_shop.base.BaseTestWithDatabase;
import piotrusha.e_shop.core.model.Product;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.builder.ProductBuilder;
import piotrusha.e_shop.core.model.builder.PurchaseProductBuilder;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;
import piotrusha.e_shop.stub.model.Products.TestProductBeer;

import java.util.List;

class PurchaseProductServiceTest extends BaseTestWithDatabase {

    @Autowired
    private PurchaseProductService purchaseProductService;

    @Test
    void preparePurchaseProducts() {
        int soldPiecesNumber1 = 4;
        int soldPiecesNumber2 = 1;
        List<PurchaseProductDto> dtos = List.of(new PurchaseProductDto(TestProductBeer.ID, soldPiecesNumber1),
                                                new PurchaseProductDto(TestProductBread.ID, soldPiecesNumber2));
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductWithUpdatedProduct(TestProductBeer.PRODUCT, soldPiecesNumber1),
                                                                 createPurchaseProductWithUpdatedProduct(TestProductBread.PRODUCT, soldPiecesNumber2));

        List<PurchaseProduct> purchaseProducts = purchaseProductService.preparePurchaseProducts(dtos);

        assertPurchaseProducts(expectedPurchaseProducts, purchaseProducts);
    }

    @Test
    void cancelPurchaseProducts() {
        int soldPiecesNumber1 = 4;
        int soldPiecesNumber2 = 1;
        List<PurchaseProduct> purchaseProducts = List.of(createPurchaseProductWithUpdatedProduct(TestProductBeer.PRODUCT, soldPiecesNumber1),
                                                         createPurchaseProductWithUpdatedProduct(TestProductBread.PRODUCT, soldPiecesNumber2));
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductWithoutUpdatedProduct(TestProductBeer.PRODUCT, soldPiecesNumber1),
                                                                 createPurchaseProductWithoutUpdatedProduct(TestProductBread.PRODUCT, soldPiecesNumber2));

        purchaseProductService.cancelPurchaseProducts(purchaseProducts);

        assertPurchaseProducts(expectedPurchaseProducts, purchaseProducts);
    }

    private PurchaseProduct createPurchaseProductWithoutUpdatedProduct(Product product, int soldPiecesNumber) {
        return new PurchaseProductBuilder(new ProductBuilder(product).id(product.getId())
                                                                     .availablePiecesNumber(product.getAvailablePiecesNumber())
                                                                     .soldPiecesNumber(product.getSoldPiecesNumber())
                                                                     .build(),
                                          soldPiecesNumber).build();
    }

}