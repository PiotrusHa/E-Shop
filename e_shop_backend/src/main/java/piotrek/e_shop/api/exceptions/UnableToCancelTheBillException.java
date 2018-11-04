package piotrek.e_shop.api.exceptions;

import piotrek.e_shop.model.BillState;

import java.math.BigDecimal;

public class UnableToCancelTheBillException extends RuntimeException {

    private BigDecimal billId;

    private BillState billState;

    public UnableToCancelTheBillException(BigDecimal billId, BillState billState) {
        super(String.format("Unable to cancel the bill with id %s: bill should be in %s state, but current state is %s",
                            String.valueOf(billId), BillState.WAITING_FOR_PAYMENT.name(), billState.name()));
        this.billId = billId;
        this.billState = billState;
    }

    public BigDecimal getBillId() {
        return billId;
    }

    public BillState getBillState() {
        return billState;
    }

}
