package piotrusha.e_shop.core.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createCreateBillDto;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createCreateBillRecordDto;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createProductDtoForBillRecord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.BillRecordDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.core.product.domain.ProductFacade;
import piotrusha.e_shop.core.product.domain.dto.ProductDto;
import piotrusha.e_shop.core.product.domain.exception.ProductNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class BillCreationTest {

    private ProductFacade productFacade;
    private BillFacade billFacade;

    @BeforeEach
    void init() {
        productFacade = mock(ProductFacade.class);
        billFacade = new BillConfiguration().billFacade(productFacade);
    }

    @Test
    void createWithNonexistentProductId() {
        BigDecimal nonexistentProductId = BigDecimal.TEN;
        when(productFacade.findProductByProductId(nonexistentProductId))
                .thenReturn(Optional.empty());
        CreateBillDto dto = createCreateBillDto(List.of(createCreateBillRecordDto(nonexistentProductId)));
        String expectedMessage = String.format("Product with productId %s not found", nonexistentProductId);

        ProductNotFoundException e = assertThrows(ProductNotFoundException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void create() {
        BigDecimal product1Id = BigDecimal.ONE;
        BigDecimal product2Id = BigDecimal.valueOf(2);
        BigDecimal product1Price = BigDecimal.valueOf(11);
        BigDecimal product2Price = BigDecimal.valueOf(20);
        Integer product1Pieces = 3;
        Integer product2Pieces = 6;
        CreateBillRecordDto recordDto1 = new CreateBillRecordDto(product1Id, product1Pieces);
        CreateBillRecordDto recordDto2 = new CreateBillRecordDto(product2Id, product2Pieces);
        ProductDto product1 = createProductDtoForBillRecord(recordDto1, product1Price);
        ProductDto product2 = createProductDtoForBillRecord(recordDto2, product2Price);
        when(productFacade.findProductByProductId(product1Id))
                .thenReturn(Optional.of(product1));
        when(productFacade.findProductByProductId(product2Id))
                .thenReturn(Optional.of(product2));
        CreateBillDto createBillDto = createCreateBillDto(List.of(recordDto1, recordDto2));
        BigDecimal expectedPriceSum = BigDecimal.valueOf(153);

        BillDto billDto = billFacade.createBill(createBillDto);

        assertEquals(expectedPriceSum, billDto.getPriceSum());
        assertRecord(recordDto1, product1, billDto.getBillRecords().get(0));
    }

    private void assertRecord(CreateBillRecordDto recordDto, ProductDto product, BillRecordDto billRecordDto) {
        BigDecimal expectedPriceSum = product.getPrice().multiply(BigDecimal.valueOf(recordDto.getPiecesNumber()));
        assertEquals(recordDto.getPiecesNumber(), billRecordDto.getPiecesNumber());
        assertEquals(recordDto.getProductId(), billRecordDto.getProductId());
        assertEquals(product.getPrice(), billRecordDto.getPiecePrice());
        assertEquals(expectedPriceSum, billRecordDto.getPriceSum());
    }

}
