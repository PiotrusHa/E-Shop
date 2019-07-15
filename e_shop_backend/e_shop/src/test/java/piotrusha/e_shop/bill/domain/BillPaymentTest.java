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
import piotrusha.e_shop.product.domain.dto.SellProductDto;

import java.util.List;
import java.util.stream.Collectors;

class BillPaymentTest extends BillTest {

    @Test
    void payBill() {
        BillDto billDto = prepareBillToPay();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        BillDto expectedChangedBill = expectedBillDto(billDto);

        Either<AppError, BillDto> result = billFacade.payBill(dto);

        assertBillDto(expectedChangedBill, result.get());
        assertWithBillFromDatabase(expectedChangedBill.getBillId(), expectedChangedBill);
    }

    private BillDto prepareBillToPay() {
        BillDto billDto = prepareBill();
        List<SellProductDto> expectedSellProductDtos = billDto.getBillRecords()
                                                              .stream()
                                                              .map(record -> new SellProductDto(record.getProductId(),
                                                                                                record.getPiecesNumber()))
                                                              .collect(Collectors.toList());
        when(productFacade.sellProducts(eq(expectedSellProductDtos)))
                .thenReturn(Either.right(null));

        return billDto;
    }

    private BillDto expectedBillDto(BillDto currentBill) {
        return currentBill.toBuilder()
                          .billState("PAID")
                          .build();
    }

    @Test
    void payAlreadyPaidBill() {
        BillDto billDto = preparePaidBill();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        String expectedErrorMessage = "Cannot pay bill with state PAID";
        ErrorType expectedErrorType = ErrorType.CANNOT_PAY_BILL;

        Either<AppError, BillDto> result = billFacade.payBill(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertBillDidNotChange(billDto);
    }

    @Test
    void payCancelledBill() {
        BillDto billDto = prepareCancelledBill();
        BillActionDto dto = new BillActionDto(billDto.getBillId());
        String expectedErrorMessage = "Cannot pay bill with state CANCELLED";
        ErrorType expectedErrorType = ErrorType.CANNOT_PAY_BILL;

        Either<AppError, BillDto> result = billFacade.payBill(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
        assertBillDidNotChange(billDto);
    }

}
