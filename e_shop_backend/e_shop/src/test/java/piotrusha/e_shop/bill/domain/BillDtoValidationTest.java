package piotrusha.e_shop.bill.domain;

import org.junit.jupiter.api.BeforeEach;

class BillDtoValidationTest {

    protected BillFacade billFacade;

    @BeforeEach
    protected void init() {
        billFacade = new BillConfiguration().billFacade(null, null);
    }

}
