package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDto;
import static piotrusha.e_shop.bill.domain.SampleDtos.createCreateBillRecordDto;
import static piotrusha.e_shop.bill.domain.SampleDtos.createProductDtoForBillRecord;

import com.google.common.collect.Ordering;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.DateTimeProvider;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.BillRecordDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.product.domain.ProductFacade;
import piotrusha.e_shop.product.domain.dto.BookProductDto;
import piotrusha.e_shop.product.domain.dto.ProductDto;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

class BillCreationTest {

    private ProductFacade productFacade;
    private BillFacade billFacade;

    @BeforeEach
    void init() {
        productFacade = mock(ProductFacade.class);
        billFacade = new BillConfiguration().billFacade(new DateTimeProvider(), productFacade);
    }

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
        List<CreateBillDto.CreateBillRecordDto> createBillRecordDtos = prepareBillRecords();
        CreateBillDto createBillDto = createBillDto(createBillRecordDtos);
        BigDecimal expectedPriceSum = getPriceSumForPreparedRecords();

        Either<AppError, BillDto> billDto = billFacade.createBill(createBillDto);

        assertTrue(billDto.isRight());
        assertEquals(expectedPriceSum, billDto.get().getPriceSum());
        assertEquals(createBillDto.getClientId(), billDto.get().getClientId());
        assertRecords(createBillRecordDtos, billDto.get().getBillRecords());
    }

    private List<CreateBillDto.CreateBillRecordDto> prepareBillRecords() {
        BigDecimal product1Id = BigDecimal.ONE;
        BigDecimal product2Id = BigDecimal.valueOf(2);
        BigDecimal product1Price = BigDecimal.valueOf(11);
        BigDecimal product2Price = BigDecimal.valueOf(20);
        Integer product1Pieces = 3;
        Integer product2Pieces = 6;
        CreateBillDto.CreateBillRecordDto recordDto1 = new CreateBillDto.CreateBillRecordDto(product1Id, product1Pieces);
        CreateBillDto.CreateBillRecordDto recordDto2 = new CreateBillDto.CreateBillRecordDto(product2Id, product2Pieces);
        ProductDto product1 = createProductDtoForBillRecord(recordDto1, product1Price);
        ProductDto product2 = createProductDtoForBillRecord(recordDto2, product2Price);

        BookProductDto bookProduct1Dto = new BookProductDto(product1Id, product1Pieces);
        BookProductDto bookProduct2Dto = new BookProductDto(product2Id, product2Pieces);
        when(productFacade.bookProducts(eq(List.of(bookProduct1Dto, bookProduct2Dto))))
                .thenReturn(Either.right(List.of(product1, product2)));

        return List.of(recordDto1, recordDto2);
    }

    private BigDecimal getPriceSumForPreparedRecords() {
        return BigDecimal.valueOf(153);
    }

    private void assertRecords(List<CreateBillDto.CreateBillRecordDto> createBillRecordDtos, List<BillRecordDto> billRecordDtos) {
        assertEquals(createBillRecordDtos.size(), billRecordDtos.size());

        List<CreateBillDto.CreateBillRecordDto> sortedCreateBRDtos = Ordering.from(Comparator.comparing(CreateBillDto.CreateBillRecordDto::getProductId))
                                                                             .sortedCopy(createBillRecordDtos);
        List<BillRecordDto> sortedBRDtos = Ordering.from(Comparator.comparing(BillRecordDto::getProductId))
                                                   .sortedCopy(billRecordDtos);
        for (int i = 0; i < sortedCreateBRDtos.size(); i++) {
            assertRecord(sortedCreateBRDtos.get(i), sortedBRDtos.get(i));
        }
    }

    private void assertRecord(CreateBillDto.CreateBillRecordDto createBillRecordDto, BillRecordDto billRecordDto) {
        assertEquals(createBillRecordDto.getPiecesNumber(), billRecordDto.getPiecesNumber());
        assertEquals(createBillRecordDto.getProductId(), billRecordDto.getProductId());
    }

}
