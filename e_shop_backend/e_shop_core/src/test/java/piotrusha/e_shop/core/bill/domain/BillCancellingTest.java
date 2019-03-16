package piotrusha.e_shop.core.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.exception.BillNotFoundException;
import piotrusha.e_shop.core.bill.domain.exception.BillValidationException;
import piotrusha.e_shop.core.product.domain.ProductFacade;

import java.math.BigDecimal;

class BillCancellingTest {

    private BillFacade billFacade;
    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = mock(ProductFacade.class);
        billFacade = new BillConfiguration().billFacade(productFacade);
    }

    @Test
    void cancelBillEmptyId() {
        BillActionDto dto = new BillActionDto(null);
        String expectedMessage = "Bill id cannot be empty.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.cancelBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void cancelBillNonexistentBill() {
        BigDecimal nonexistentId = BigDecimal.TEN;
        BillActionDto dto = new BillActionDto(nonexistentId);
        String expectedMessage = String.format("Bill with billId %s not found.", nonexistentId);

        BillNotFoundException e = assertThrows(BillNotFoundException.class, () -> billFacade.cancelBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void cancelAlreadyCancelledBill() {
        // TODO
    }

    @Test
    void cancelPaidBill() {
        // TODO
    }

    @Test
    void cancelBillWithExceededPaymentTime() {
        // TODO
    }

    @Test
    void cancelBill() {
        // TODO
    }

}
