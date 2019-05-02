package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static piotrusha.e_shop.bill.domain.SampleDtos.createProductDtoForBillRecord;

import com.google.common.collect.Ordering;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.DateTimeProvider;
import piotrusha.e_shop.base.TestDateTimeProvider;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

class BillTest {

    protected ProductFacade productFacade;
    protected BillFacade billFacade;
    protected DateTimeProvider dateTimeProvider;

    @BeforeEach
    void init() {
        dateTimeProvider = new TestDateTimeProvider();
        productFacade = mock(ProductFacade.class);
        billFacade = new BillConfiguration().billFacade(dateTimeProvider, productFacade);
    }

    BillDto prepareBill() {
        BigDecimal productId = BigDecimal.ONE;
        int piecesNumber = 3;
        BigDecimal productPrice = BigDecimal.TEN;
        CreateBillRecordDto recordDto1 = new CreateBillRecordDto(productId, piecesNumber);
        ProductDto product1 = createProductDtoForBillRecord(recordDto1, productPrice);
        BookProductDto bookProduct1Dto = new BookProductDto(productId, piecesNumber);
        when(productFacade.bookProducts(eq(List.of(bookProduct1Dto))))
                .thenReturn(Either.right(List.of(product1)));

        CreateBillDto createBillDto = new CreateBillDto(BigDecimal.ONE, List.of(recordDto1));

        return billFacade.createBill(createBillDto).get();
    }

    BillDto prepareCancelledBill() {
        BillDto billDto = prepareBill();
        when(productFacade.cancelBooking(any()))
                .thenReturn(Either.right(null));
        return billFacade.cancelBill(new BillActionDto(billDto.getBillId())).get();
    }

    BillDto preparePaidBill() {
        BillDto billDto = prepareBill();
        when(productFacade.sellProducts(any()))
                .thenReturn(Either.right(null));
        return billFacade.payBill(new BillActionDto(billDto.getBillId())).get();
    }

    void assertBillDidNotChange(BillDto billDto) {
        assertWithBillFromDatabase(billDto.getBillId(), billDto);
    }

    void assertWithBillFromDatabase(BigDecimal billId, BillDto expected) {
        Either<AppError, BillDto> bill = billFacade.findBillByBillId(billId);
        assertTrue(bill.isRight());
        assertBillDto(expected, bill.get());
    }

    void assertBillDto(BillDto expected, BillDto actual) {
        assertEquals(expected.getBillId(), actual.getBillId());
        assertEquals(expected.getPriceSum(), actual.getPriceSum());
        assertEquals(expected.getPurchaseDate(), actual.getPurchaseDate());
        assertEquals(expected.getPaymentDate(), actual.getPaymentDate());
        assertEquals(expected.getPaymentExpirationDate(), actual.getPaymentExpirationDate());
        assertEquals(expected.getClientId(), actual.getClientId());
        assertEquals(expected.getBillState(), actual.getBillState());
        assertBillRecords(expected.getBillRecords(), actual.getBillRecords());
    }

    private void assertBillRecords(List<BillRecordDto> expected, List<BillRecordDto> actual) {
        assertEquals(expected.size(), actual.size());

        expected = sortBillRecords(expected);
        actual = sortBillRecords(actual);

        for (int i = 0; i < expected.size(); i++) {
            assertBillRecord(expected.get(i), actual.get(i));
        }
    }

    private List<BillRecordDto> sortBillRecords(List<BillRecordDto> recordDtos) {
        return Ordering.from(Comparator.comparing(BillRecordDto::getProductId))
                       .sortedCopy(recordDtos);
    }

    private void assertBillRecord(BillRecordDto expected, BillRecordDto actual) {
        assertEquals(expected.getProductId(), actual.getProductId());
        assertEquals(expected.getPiecesNumber(), actual.getPiecesNumber());
        assertEquals(expected.getPiecePrice(), actual.getPiecePrice());
    }

}
