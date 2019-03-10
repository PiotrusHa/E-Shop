package piotrusha.e_shop.core.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createCreateBillDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.core.bill.domain.exception.BillValidationException;

import java.math.BigDecimal;
import java.util.List;

class BillCreationDtoValidationTest {

    private BillFacade billFacade;

    @BeforeEach
    void init() {
        billFacade = new BillConfiguration().billFacade(null);
    }

    @Test
    void createBillWithEmptyClientId() {
        CreateBillDto dto = createCreateBillDto().setClientId(null);
        String expectedMessage = "Client id cannot be empty.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithNullRecords() {
        CreateBillDto dto = createCreateBillDto().setRecords(null);
        String expectedMessage = "Bill records cannot be empty.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithEmptyRecordList() {
        CreateBillDto dto = createCreateBillDto().setRecords(List.of());
        String expectedMessage = "Bill records cannot be empty.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithEmptyRecordProductId() {
        CreateBillDto dto = createCreateBillDto().setRecords(List.of(new CreateBillRecordDto(null, 2)));
        String expectedMessage = "Product id cannot be empty.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithEmptyRecordPiecesNumber() {
        CreateBillDto dto = createCreateBillDto().setRecords(List.of(new CreateBillRecordDto(BigDecimal.ONE, null)));
        String expectedMessage = "Pieces number has to be greater than zero.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithZeroRecordPiecesNumber() {
        CreateBillDto dto = createCreateBillDto().setRecords(List.of(new CreateBillRecordDto(BigDecimal.ONE, 0)));
        String expectedMessage = "Pieces number has to be greater than zero.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void createBillWithNegativeRecordPiecesNumber() {
        CreateBillDto dto = createCreateBillDto().setRecords(List.of(new CreateBillRecordDto(BigDecimal.ONE, -2)));
        String expectedMessage = "Pieces number has to be greater than zero.";

        BillValidationException e = assertThrows(BillValidationException.class, () -> billFacade.createBill(dto));

        assertEquals(expectedMessage, e.getMessage());
    }

}
