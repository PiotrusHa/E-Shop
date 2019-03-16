package piotrusha.e_shop.core.bill.domain.exception;

import java.math.BigDecimal;

public class BillNotFoundException extends RuntimeException {

    public BillNotFoundException(BigDecimal billId) {
        super(String.format("Bill with billId %s not found.", billId));
    }

}
