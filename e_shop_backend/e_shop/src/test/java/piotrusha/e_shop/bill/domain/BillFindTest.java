package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.bill.domain.dto.BillDto;

import java.math.BigDecimal;
import java.util.List;

class BillFindTest {

    private BillFacade billFacade;

    @BeforeEach
    void init() {
        billFacade = new BillConfiguration().billFacade(null);
    }

    @Test
    void findBillWithBillState() {
        String state = "PAID";

        Either<AppError, List<BillDto>> result = billFacade.findBillsByClientIdAndBillState(BigDecimal.ONE, state);

        assertTrue(result.isRight());
    }

    @Test
    void findBillWithBillWithWrongState() {
        String state = "wrong state";
        String expectedErrorMessage = "Wrong bill state: " + state;
        AppError.ErrorType expectedErrorType = AppError.ErrorType.VALIDATION;

        Either<AppError, List<BillDto>> result = billFacade.findBillsByClientIdAndBillState(BigDecimal.ONE, state);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

}
