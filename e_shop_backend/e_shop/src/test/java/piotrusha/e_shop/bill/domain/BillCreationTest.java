package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDto;
import static piotrusha.e_shop.bill.domain.SampleDtos.createCreateBillRecordDto;
import static piotrusha.e_shop.bill.domain.SampleDtos.createProductDtoForBillRecord;

import io.vavr.control.Either;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.List;

class BillCreationTest extends BillTest {

    private static final BigDecimal PRODUCT_1_ID = BigDecimal.ONE;
    private static final BigDecimal PRODUCT_2_ID = BigDecimal.TEN;
    private static final int PRODUCT_1_PIECES = 3;
    private static final int PRODUCT_2_PIECES = 6;
    private static final BigDecimal PRODUCT_1_PRICE = BigDecimal.valueOf(11);
    private static final BigDecimal PRODUCT_2_PRICE = BigDecimal.valueOf(20);
    private static final BigDecimal PRICE_SUM = BigDecimal.valueOf(153);

    @Test
    void createWithErrorWhileBookingProduct() {
        String errorMessage = "Field cannot be empty.";
        CreateBillDto createBillDto = createBillDto(List.of(createCreateBillRecordDto(BigDecimal.ONE)));
        when(productFacade.bookProducts(any()))
                .thenReturn(Either.left(AppError.emptyDtoField("Field")));

        Either<AppError, BillDto> result = billFacade.createBill(createBillDto);

        assertTrue(result.isLeft());
        assertEquals(errorMessage, result.getLeft().getErrorMessage());
    }

    @Test
    void create() {
        CreateBillDto createBillDto = prepareBillDto();
        BillDto expectedCreatedBill = expectedBillDto(createBillDto);

        Either<AppError, BillDto> result = billFacade.createBill(createBillDto);

        assertTrue(result.isRight());
        assertBillDto(expectedCreatedBill, result.get());
        assertWithBillFromDatabase(result.get().getBillId(), expectedCreatedBill);
    }

    private CreateBillDto prepareBillDto() {
        return new CreateBillDto(BigDecimal.ONE, prepareBillRecords());
    }

    private List<CreateBillRecordDto> prepareBillRecords() {
        CreateBillRecordDto recordDto1 = new CreateBillRecordDto(PRODUCT_1_ID, PRODUCT_1_PIECES);
        CreateBillRecordDto recordDto2 = new CreateBillRecordDto(PRODUCT_2_ID, PRODUCT_2_PIECES);
        ProductDto product1 = createProductDtoForBillRecord(recordDto1, PRODUCT_1_PRICE);
        ProductDto product2 = createProductDtoForBillRecord(recordDto2, PRODUCT_2_PRICE);

        BookProductDto bookProduct1Dto = new BookProductDto(PRODUCT_1_ID, PRODUCT_1_PIECES);
        BookProductDto bookProduct2Dto = new BookProductDto(PRODUCT_2_ID, PRODUCT_2_PIECES);
        when(productFacade.bookProducts(eq(List.of(bookProduct1Dto, bookProduct2Dto))))
                .thenReturn(Either.right(List.of(product1, product2)));

        return List.of(recordDto1, recordDto2);
    }

    private BillDto expectedBillDto(CreateBillDto createBillDto) {
        return BillDto.builder()
                      .billId(BigDecimal.ONE)
                      .priceSum(PRICE_SUM)
                      .purchaseDate(dateTimeProvider.currentDateTime())
                      .paymentExpirationDate(dateTimeProvider.currentDate().plusDays(7))
                      .clientId(createBillDto.getClientId())
                      .billState("WAITING_FOR_PAYMENT")
                      .billRecords(expectedBillRecords())
                      .build();
    }

    private List<BillRecordDto> expectedBillRecords() {
        return List.of(new BillRecordDto(PRODUCT_1_ID, PRODUCT_1_PIECES, PRODUCT_1_PRICE),
                       new BillRecordDto(PRODUCT_2_ID, PRODUCT_2_PIECES, PRODUCT_2_PRICE));
    }

}
