package piotrusha.e_shop.core.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import piotrusha.e_shop.core.exception.UnableToCancelTheBillException;
import piotrusha.e_shop.core.exception.UnableToPayTheBillException;
import piotrusha.e_shop.base.BaseTestWithDatabase;
import piotrusha.e_shop.core.model.Bill;
import piotrusha.e_shop.core.model.BillState;
import piotrusha.e_shop.core.model.PurchaseProduct;
import piotrusha.e_shop.core.model.builder.BillBuilder;
import piotrusha.e_shop.core.model.dto.PurchaseProductDto;
import piotrusha.e_shop.stub.model.Bills;
import piotrusha.e_shop.stub.model.Bills.BillCancelled;
import piotrusha.e_shop.stub.model.Bills.BillPaid;
import piotrusha.e_shop.stub.model.Bills.BillWaitingForPayment;
import piotrusha.e_shop.stub.model.Bills.BillWithExceededPaymentTime;
import piotrusha.e_shop.stub.model.Bills.BillWithExceededPaymentTimeButStatusNotSet;
import piotrusha.e_shop.stub.model.Products.TestProductBeer;
import piotrusha.e_shop.stub.model.Products.TestProductBread;
import piotrusha.e_shop.stub.model.Products.TestProductDesertEagle;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

class BillServiceTest extends BaseTestWithDatabase {

    @Autowired
    private BillService billService;

    @Test
    void findBillsByClientId() {
        BigDecimal clientId = BigDecimal.ONE;
        List<Bill> expectedResult = Bills.TEST_BILLS;

        List<Bill> result = billService.findBillsByClientId(clientId);

        assertBills(expectedResult, result);
    }

    @ParameterizedTest
    @MethodSource("clientIdAndStateProvider")
    void findBillsByClientIdAndState(BigDecimal clientId, BillState state, List<Bill> expectedResult) {
        List<Bill> result = billService.findBillsByClientIdAndState(clientId, state);

        assertBills(expectedResult, result);
    }

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

   @Test
   void payBill() {
        Bill billToPay = BillWaitingForPayment.BILL;
        Bill expectedBill = new BillBuilder(BillWaitingForPayment.BILL).state(BillState.PAID).build();

        Bill result = billService.payBill(billToPay.getId());

        assertBillWithoutId(expectedBill, result);
        assertNotNull(result.getPaymentDate());
   }

   @ParameterizedTest
   @MethodSource("billUnableToPayOrCancelProvider")
   void payBillThrowUnableToPayTheBillException(Bill bill) {
       UnableToPayTheBillException exception = assertThrows(UnableToPayTheBillException.class, () -> billService.payBill(bill.getId()));
       assertEquals(bill.getId(), exception.getBillId());
       assertEquals(bill.getState(), exception.getBillState());
   }

    @Test
    void cancelBill() {
        Bill billToCancel = BillWaitingForPayment.BILL;
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductToCancel(TestProductBeer.PRODUCT, 1),
                                                                 createPurchaseProductToCancel(TestProductDesertEagle.PRODUCT, 1));
        Bill expectedBill = new BillBuilder(BillWaitingForPayment.BILL).id(BillWaitingForPayment.ID)
                                                                       .state(BillState.CANCELLED)
                                                                       .purchaseProducts(expectedPurchaseProducts)
                                                                       .build();

        Bill result = billService.cancelBill(billToCancel.getId());

        assertBill(expectedBill, result);
    }

    @ParameterizedTest
    @MethodSource("billUnableToPayOrCancelProvider")
    void cancelBillThrowUnableToCancelTheBillException(Bill bill) {
        UnableToCancelTheBillException exception = assertThrows(UnableToCancelTheBillException.class, () -> billService.cancelBill(bill.getId()));
        assertEquals(bill.getId(), exception.getBillId());
        assertEquals(bill.getState(), exception.getBillState());
    }

    @Test
    void markBillsWithExpiredPaymentDate() {
        List<PurchaseProduct> expectedPurchaseProducts = List.of(createPurchaseProductToCancel(TestProductBread.PRODUCT, 9),
                                                                 createPurchaseProductToCancel(TestProductBeer.PRODUCT, 10));
        List<Bill> expectedBills = List.of(
                new BillBuilder(BillWithExceededPaymentTimeButStatusNotSet.BILL).id(BillWithExceededPaymentTimeButStatusNotSet.ID)
                                                                                .state(BillState.PAYMENT_TIME_EXCEEDED)
                                                                                .purchaseProducts(expectedPurchaseProducts)
                                                                                .build());

        List<Bill> result = billService.markBillsWithExpiredPaymentDate();

        assertBills(expectedBills, result);
    }

    private static Stream<Arguments> billUnableToPayOrCancelProvider() {
        return Stream.of(
                Arguments.of(BillCancelled.BILL),
                Arguments.of(BillPaid.BILL),
                Arguments.of(BillWithExceededPaymentTime.BILL)
        );
    }

    private static Stream<Arguments> clientIdAndStateProvider() {
        BigDecimal clientId = BigDecimal.ONE;
        return Stream.of(
                Arguments.of(clientId, BillState.WAITING_FOR_PAYMENT, List.of(BillWaitingForPayment.BILL,
                                                                              BillWithExceededPaymentTimeButStatusNotSet.BILL)),
                Arguments.of(clientId, BillState.PAID, List.of(BillPaid.BILL)),
                Arguments.of(clientId, BillState.PAYMENT_TIME_EXCEEDED, List.of(BillWithExceededPaymentTime.BILL)),
                Arguments.of(clientId, BillState.CANCELLED, List.of(BillCancelled.BILL))
        );
    }

}