package piotrek.e_shop.core.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.services.BillService;
import piotrek.e_shop.base.BaseTestWithDatabase;
import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.BillBuilder;
import piotrek.e_shop.model.dto.PurchaseProductDto;
import piotrek.e_shop.stub.model.Products.TestProductBeer;
import piotrek.e_shop.stub.model.Products.TestProductBread;

import java.math.BigDecimal;
import java.util.List;

class BillServiceTest extends BaseTestWithDatabase {

    @Autowired
    private BillService billService;

    @Test
    void createBill() {
        BigDecimal clientId = BigDecimal.ONE;
        int soldPiecesNumber1 = 4;
        int soldPiecesNumber2 = 1;
        List<PurchaseProductDto> dtos = List.of(new PurchaseProductDto(TestProductBeer.ID, soldPiecesNumber1),
                                                new PurchaseProductDto(TestProductBread.ID, soldPiecesNumber2));
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductWithUpdatedProduct(TestProductBeer.PRODUCT, soldPiecesNumber1),
                                                                 createPurchaseProductWithUpdatedProduct(TestProductBread.PRODUCT, soldPiecesNumber2));
        Bill expectedBill = new BillBuilder().clientId(clientId)
                                             .purchaseProducts(expectedPurchaseProducts)
                                             .priceSum(BigDecimal.valueOf(27.94))
                                             .state(BillState.WAITING_FOR_PAYMENT)
                                             .build();

        Bill bill = billService.createBill(dtos, clientId);

        assertBillWithoutId(expectedBill, bill);
   }

}