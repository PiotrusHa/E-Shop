package piotrek.e_shop.stub.model;

import piotrek.e_shop.model.Bill;
import piotrek.e_shop.model.BillState;
import piotrek.e_shop.model.builder.BillBuilder;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct10Beers;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct1Beer;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct1Bread;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct1DesertEagle;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct3Breads;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct4Beers;
import piotrek.e_shop.stub.model.PurchaseProducts.PurchaseProduct9Breads;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Bills {

    private static final BigDecimal CLIENT_ID = BigDecimal.ONE;
    private static final Date CURRENT_DATE = new Date();
    private static final int ONE_DAY_MILLIS = 86400000;

    public static class BillWaitingForPayment {
        public static final BigDecimal ID = BigDecimal.valueOf(1);

        public static final Bill BILL = new BillBuilder().id(ID)
                                                         .state(BillState.WAITING_FOR_PAYMENT)
                                                         .purchaseDate(new Date(CURRENT_DATE.getTime() - ONE_DAY_MILLIS))
                                                         .paymentExpirationDate(new Date(CURRENT_DATE.getTime() + 6*ONE_DAY_MILLIS))
                                                         .purchaseProducts(List.of(PurchaseProduct1Beer.PURCHASE_PRODUCT,
                                                                                   PurchaseProduct1DesertEagle.PURCHASE_PRODUCT))
                                                         .priceSum(BigDecimal.valueOf(603.98))
                                                         .clientId(CLIENT_ID)
                                                         .build();
    }

    public static class BillWithExceededPaymentTime {
        public static final BigDecimal ID = BigDecimal.valueOf(2);

        public static final Bill BILL = new BillBuilder().id(ID)
                                                         .state(BillState.PAYMENT_TIME_EXCEEDED)
                                                         .purchaseDate(new Date(CURRENT_DATE.getTime() - 10*ONE_DAY_MILLIS))
                                                         .paymentExpirationDate(new Date(CURRENT_DATE.getTime() - 3*ONE_DAY_MILLIS))
                                                         .purchaseProducts(List.of(PurchaseProduct3Breads.PURCHASE_PRODUCT))
                                                         .priceSum(BigDecimal.valueOf(35.94))
                                                         .clientId(CLIENT_ID)
                                                         .build();
    }

    public static class BillPaid {
        public static final BigDecimal ID = BigDecimal.valueOf(3);

        public static final Bill BILL = new BillBuilder().id(ID)
                                                         .state(BillState.PAID)
                                                         .purchaseDate(new Date(CURRENT_DATE.getTime() - 3*ONE_DAY_MILLIS))
                                                         .paymentExpirationDate(new Date(CURRENT_DATE.getTime() + 2*ONE_DAY_MILLIS))
                                                         .paymentDate(new Date(CURRENT_DATE.getTime() - ONE_DAY_MILLIS))
                                                         .purchaseProducts(List.of(PurchaseProduct4Beers.PURCHASE_PRODUCT))
                                                         .priceSum(BigDecimal.valueOf(15.96))
                                                         .clientId(CLIENT_ID)
                                                         .build();
    }

    public static class BillCancelled {
        public static final BigDecimal ID = BigDecimal.valueOf(4);

        public static final Bill BILL = new BillBuilder().id(ID)
                                                         .state(BillState.CANCELLED)
                                                         .purchaseDate(new Date(CURRENT_DATE.getTime() - ONE_DAY_MILLIS))
                                                         .paymentExpirationDate(new Date(CURRENT_DATE.getTime() + 6*ONE_DAY_MILLIS))
                                                         .purchaseProducts(List.of(PurchaseProduct1Bread.PURCHASE_PRODUCT))
                                                         .priceSum(BigDecimal.valueOf(11.98))
                                                         .clientId(CLIENT_ID)
                                                         .build();
    }

    public static class BillWithExceededPaymentTimeButStatusNotSet {
        public static final BigDecimal ID = BigDecimal.valueOf(5);

        public static final Bill BILL = new BillBuilder().id(ID)
                                                         .state(BillState.WAITING_FOR_PAYMENT)
                                                         .purchaseDate(new Date(CURRENT_DATE.getTime() - 6*ONE_DAY_MILLIS))
                                                         .paymentExpirationDate(new Date(CURRENT_DATE.getTime() - ONE_DAY_MILLIS))
                                                         .purchaseProducts(List.of(PurchaseProduct9Breads.PURCHASE_PRODUCT,
                                                                                   PurchaseProduct10Beers.PURCHASE_PRODUCT))
                                                         .priceSum(BigDecimal.valueOf(147.72))
                                                         .clientId(CLIENT_ID)
                                                         .build();
    }

    public static final List<Bill> TEST_BILLS = List.of(BillWaitingForPayment.BILL,
                                                   BillWithExceededPaymentTime.BILL,
                                                   BillPaid.BILL,
                                                   BillCancelled.BILL,
                                                   BillWithExceededPaymentTimeButStatusNotSet.BILL);

}
