package piotrusha.e_shop.core.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithEmptyClientId;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithEmptyPiecesNumber;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithEmptyRecordProductId;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithEmptyRecords;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.core.bill.domain.SampleDtos.createBillDtoWithZeroPiecesNumber;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.base.AppError.ErrorType;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;

import java.util.stream.Stream;

class BillCreationDtoValidationTest {

    private BillFacade billFacade;

    @BeforeEach
    void init() {
        billFacade = new BillConfiguration().billFacade(null);
    }

    @ParameterizedTest
    @MethodSource("createBillValidationProvider")
    void createBillValidationTest(CreateBillDto dto, String expectedErrorMessage, ErrorType expectedErrorType) {
        Either<AppError, BillDto> result = billFacade.createBill(dto);

        assertTrue(result.isLeft());
        assertEquals(expectedErrorMessage, result.getLeft().getErrorMessage());
        assertEquals(expectedErrorType, result.getLeft().getErrorType());
    }

    private static Stream<Arguments> createBillValidationProvider() {
        Arguments emptyClientId = Arguments.of(createBillDtoWithEmptyClientId(), "Client id cannot be empty.", ErrorType.VALIDATION);
        Arguments emptyRecords = Arguments.of(createBillDtoWithEmptyRecords(), "Bill records cannot be empty.", ErrorType.VALIDATION);
        Arguments emptyRecordProductId = Arguments.of(createBillDtoWithEmptyRecordProductId(), "Product id cannot be empty.",
                                                      ErrorType.VALIDATION);
        Arguments emptyPiecesNumber = Arguments.of(createBillDtoWithEmptyPiecesNumber(), "Pieces number has to be greater than zero.",
                                                   ErrorType.VALIDATION);
        Arguments zeroPiecesNumber = Arguments.of(createBillDtoWithZeroPiecesNumber(), "Pieces number has to be greater than zero.",
                                                  ErrorType.VALIDATION);
        Arguments negativePiecesNumber = Arguments.of(createBillDtoWithNegativePiecesNumber(), "Pieces number has to be greater than zero.",
                                                      ErrorType.VALIDATION);

        return Stream.of(emptyClientId,
                         emptyRecords,
                         emptyRecordProductId,
                         emptyPiecesNumber,
                         zeroPiecesNumber,
                         negativePiecesNumber
        );
    }

}
