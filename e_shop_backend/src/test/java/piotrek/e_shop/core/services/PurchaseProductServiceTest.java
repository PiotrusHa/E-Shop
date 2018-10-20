package piotrek.e_shop.core.services;

import static piotrek.e_shop.stub.model.Products.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.services.PurchaseProductService;
import piotrek.e_shop.base.BaseTestWithDatabase;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.dto.PurchaseProductDto;
import piotrek.e_shop.stub.model.Products.TestProductBeer;

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

}