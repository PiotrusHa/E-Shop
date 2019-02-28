package piotrusha.e_shop.core.exception;

import piotrusha.e_shop.core.model.BillState;

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