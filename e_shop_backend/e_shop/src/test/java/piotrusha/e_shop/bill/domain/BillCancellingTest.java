package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.product.domain.dto.CancelProductBookingDto;

import java.util.List;
import java.util.stream.Collectors;

class BillCancellingTest extends BillTest {

    @Test
    void cancelBill() {
        BillDto billDto = prepareBillToCancel();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        BillDto expectedChangedBill = expectedBillDto(billDto);

        Either<AppError, BillDto> result = billFacade.cancelBill(dto);

        assertBillDto(expectedChangedBill, result.get());
        assertWithBillFromDatabase(expectedChangedBill.getBillId(), expectedChangedBill);
    }

    private BillDto prepareBillToCancel() {
        BillDto billDto = prepareBill();
        List<CancelProductBookingDto> expectedCancelProductBookingDtos = billDto.getBillRecords()
                                                                                .stream()
                                                                                .map(record -> new CancelProductBookingDto(
                                                                                        record.getProductId(), record.getPiecesNumber()))
                                                                                .collect(Collectors.toList());
        when(productFacade.cancelBooking(eq(expectedCancelProductBookingDtos)))
                .thenReturn(Either.right(null));

        return billDto;
    }

    private BillDto expectedBillDto(BillDto currentBill) {
        return currentBill.toBuilder()
                          .billState("CANCELLED")
                          .build();
    }

    @Test
    void cancelAlreadyCancelledBill() {
        BillDto billDto = prepareCancelledBill();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        String expectedErrorMessage = "Cannot cancel bill with state CANCELLED";
        ErrorType expectedErrorType = ErrorType.CANNOT_CANCEL_BILL;

        Either<AppError, BillDto> result = billFacade.cancelBill(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertBillDidNotChange(billDto);
    }

    @Test
    void cancelPaidBill() {
        BillDto billDto = preparePaidBill();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        String expectedErrorMessage = "Cannot cancel bill with state PAID";
        ErrorType expectedErrorType = ErrorType.CANNOT_CANCEL_BILL;

        Either<AppError, BillDto> result = billFacade.cancelBill(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertBillDidNotChange(billDto);
    }

}
