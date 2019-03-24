package piotrusha.e_shop.bill.domain;

public enum BillState {
    WAITING_FOR_PAYMENT,
    PAID,
    CANCELLED,
    PAYMENT_TIME_EXCEEDED
}
