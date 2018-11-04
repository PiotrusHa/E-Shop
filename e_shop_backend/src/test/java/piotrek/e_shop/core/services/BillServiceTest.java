package piotrek.e_shop.core.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import piotrek.e_shop.api.exceptions.UnableToCancelTheBillException;
import piotrek.e_shop.api.exceptions.UnableToPayTheBillException;
import piotrek.e_shop.api.services.BillService;
import piotrek.e_shop.base.BaseTestWithDatabase;
import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.Product;
import piotrek.e_shop.model.PurchaseProduct;
import piotrek.e_shop.model.builder.BillBuilder;
import piotrek.e_shop.model.builder.ProductBuilder;
import piotrek.e_shop.model.builder.PurchaseProductBuilder;
import piotrek.e_shop.model.dto.PurchaseProductDto;
import piotrek.e_shop.stub.model.Bills;
import piotrek.e_shop.stub.model.Bills.BillWaitingForPayment;
import piotrek.e_shop.stub.model.Bills.BillWithExceededPaymentTimeButStatusNotSet;
import piotrek.e_shop.stub.model.Products.TestProductBeer;
import piotrek.e_shop.stub.model.Products.TestProductBread;
import piotrek.e_shop.stub.model.Products.TestProductDesertEagle;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

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
                Arguments.of(Bills.BillCancelled.BILL),
                Arguments.of(Bills.BillPaid.BILL),
                Arguments.of(Bills.BillWithExceededPaymentTime.BILL)
        );
    }

    private PurchaseProduct createPurchaseProductToCancel(Product product, int soldPiecesNumber) {
        return new PurchaseProductBuilder(new ProductBuilder(product).id(product.getId())
                                                                     .availablePiecesNumber(product.getAvailablePiecesNumber() + soldPiecesNumber)
                                                                     .soldPiecesNumber(product.getSoldPiecesNumber() - soldPiecesNumber)
                                                                     .build(),
                                          soldPiecesNumber).build();
    }

}