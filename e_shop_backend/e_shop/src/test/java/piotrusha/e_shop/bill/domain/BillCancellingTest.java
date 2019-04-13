package piotrusha.e_shop.bill.domain;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.DateTimeProvider;
import piotrusha.e_shop.product.domain.ProductFacade;

class BillCancellingTest {

    private BillFacade billFacade;
    private ProductFacade productFacade;

    @BeforeEach
    void init() {
        productFacade = mock(ProductFacade.class);
        billFacade = new BillConfiguration().billFacade(new DateTimeProvider(), productFacade);
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
