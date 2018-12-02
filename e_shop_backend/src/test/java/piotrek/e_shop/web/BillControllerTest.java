package piotrek.e_shop.web;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import piotrek.e_shop.base.BaseControllerTest;
import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.BillBuilder;
import piotrek.e_shop.model.dto.PurchaseProductDto;
import piotrek.e_shop.stub.model.Bills;
import piotrek.e_shop.stub.model.Bills.BillPaid;
import piotrek.e_shop.stub.model.Products;

import java.math.BigDecimal;
import java.util.List;

class BillControllerTest extends BaseControllerTest {

    @Autowired
    private BillController billController;

    @BeforeEach
    private void init() {
        super.init(billController);
    }

    @Test
    void getBillsByClientId() throws Exception {
        int clientId = 1;
        List<Bill> expectedResult = Bills.TEST_BILLS;

        MvcResult mvcResult = mockMvc.perform(get("/e_shop/bills/client/{clientId}", clientId))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Bill> result = readValueAsList(mvcResult, Bill.class);

        assertBills(expectedResult, result);
    }

    @Test
    void getBillsByClientIdAndState() throws Exception {
        BigDecimal clientId = BigDecimal.ONE;
        BillState state = BillState.PAID;
        List<Bill> expectedResult = List.of(BillPaid.BILL);

        MvcResult mvcResult = mockMvc.perform(get("/e_shop/bills/client/{clientId}/{state}", clientId, state))
                                     .andExpect(status().isOk())
                                     .andReturn();
        List<Bill> result = readValueAsList(mvcResult, Bill.class);

        assertBills(expectedResult, result);
    }

    @Test
    void createBill() throws Exception {
        BigDecimal clientId = BigDecimal.ONE;
        int soldPiecesNumber1 = 4;
        int soldPiecesNumber2 = 1;
        List<PurchaseProductDto> dtos = List.of(new PurchaseProductDto(Products.TestProductBeer.ID, soldPiecesNumber1),
                                                new PurchaseProductDto(Products.TestProductBread.ID, soldPiecesNumber2));
        String dtosAsJson = writeValueAsJson(dtos);
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductWithUpdatedProduct(Products.TestProductBeer.PRODUCT, soldPiecesNumber1),
                                                                 createPurchaseProductWithUpdatedProduct(Products.TestProductBread.PRODUCT, soldPiecesNumber2));
        Bill expectedBill = new BillBuilder().clientId(clientId)
                                             .purchaseProducts(expectedPurchaseProducts)
                                             .priceSum(BigDecimal.valueOf(27.94))
                                             .state(BillState.WAITING_FOR_PAYMENT)
                                             .build();

        MvcResult mvcResult = mockMvc.perform(post("/e_shop/bills/client/{clientId}/create", clientId)
                                                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                                                      .content(dtosAsJson))
                                     .andExpect(status().isOk())
                                     .andReturn();
        Bill result = readValueAsObject(mvcResult, Bill.class);

        assertBillWithoutId(expectedBill, result);
    }

    @Test
    void payBill() throws Exception {
        Bill billToPay = Bills.BillWaitingForPayment.BILL;
        Bill expectedBill = new BillBuilder(Bills.BillWaitingForPayment.BILL).state(BillState.PAID).build();

        MvcResult mvcResult = mockMvc.perform(patch("/e_shop/bills/{billId}/pay", billToPay.getId()))
                                     .andExpect(status().isOk())
                                     .andReturn();
        Bill result = readValueAsObject(mvcResult, Bill.class);

        assertBillWithoutId(expectedBill, result);
        assertNotNull(result.getPaymentDate());
    }

    @Test
    void cancelBill() throws Exception {
        Bill billToCancel = Bills.BillWaitingForPayment.BILL;
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductToCancel(Products.TestProductBeer.PRODUCT, 1),
                                                                 createPurchaseProductToCancel(Products.TestProductDesertEagle.PRODUCT, 1));
        Bill expectedBill = new BillBuilder(Bills.BillWaitingForPayment.BILL).id(Bills.BillWaitingForPayment.ID)
                                                                             .state(BillState.CANCELLED)
                                                                             .purchaseProducts(expectedPurchaseProducts)
                                                                             .build();

        MvcResult mvcResult = mockMvc.perform(patch("/e_shop/bills/{billId}/cancel", billToCancel.getId()))
                                     .andExpect(status().isOk())
                                     .andReturn();
        Bill result = readValueAsObject(mvcResult, Bill.class);

        assertBill(expectedBill, result);
    }

}