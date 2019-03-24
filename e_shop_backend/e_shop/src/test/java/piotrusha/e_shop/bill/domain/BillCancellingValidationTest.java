package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.bill.domain.SampleDtos.billActionDtoWithBillId;
import static piotrusha.e_shop.bill.domain.SampleDtos.billActionDtoWithEmptyBillId;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.BillDto;

import java.math.BigDecimal;
import java.util.stream.Stream;

class BillCancellingValidationTest {

    private BillFacade billFacade;

    @BeforeEach
    void init() {
        billFacade = new BillConfiguration().billFacade(null);
    }

    @ParameterizedTest
    @MethodSource("cancelBillValidationProvider")
    void cancelBillValidationTest(BillActionDto billActionDto, String expectedErrorMessage, ErrorType expectedErrorType) {
        Either<AppError, BillDto> result = billFacade.cancelBill(billActionDto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> cancelBillValidationProvider() {
        Arguments emptyBillId = Arguments.of(billActionDtoWithEmptyBillId(),
                                             "Bill id cannot be empty.",
                                             ErrorType.EMPTY_DTO_FIELD);
        BigDecimal nonexistentId = BigDecimal.valueOf(1410);
        Arguments nonexistentBill = Arguments.of(billActionDtoWithBillId(nonexistentId),
                                                 "Bill with billId " + nonexistentId + " not found.",
                                                 ErrorType.BILL_NOT_FOUND);

        return Stream.of(emptyBillId,
                         nonexistentBill
        );
    }

}
