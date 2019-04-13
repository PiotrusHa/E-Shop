package piotrusha.e_shop.bill.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithEmptyClientId;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithEmptyPiecesNumber;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithEmptyRecordProductId;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithEmptyRecords;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithNegativePiecesNumber;
import static piotrusha.e_shop.bill.domain.SampleDtos.createBillDtoWithZeroPiecesNumber;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.AppError.ErrorType;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;

import java.util.stream.Stream;

class BillCreationDtoValidationTest {

    private BillFacade billFacade;

    @BeforeEach
    void init() {
        billFacade = new BillConfiguration().billFacade(null, null);
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
        Arguments emptyClientId = Arguments.of(createBillDtoWithEmptyClientId(),
                                               "Client id cannot be empty.",
                                               ErrorType.EMPTY_DTO_FIELD);
        Arguments emptyRecords = Arguments.of(createBillDtoWithEmptyRecords(),
                                              "Bill records cannot be empty.",
                                              ErrorType.EMPTY_DTO_FIELD);
        Arguments emptyRecordProductId = Arguments.of(createBillDtoWithEmptyRecordProductId(),
                                                      "Product id cannot be empty.",
                                                      ErrorType.EMPTY_DTO_FIELD);
        Arguments emptyPiecesNumber = Arguments.of(createBillDtoWithEmptyPiecesNumber(),
                                                   "Pieces number has to be greater than zero.",
                                                   ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments zeroPiecesNumber = Arguments.of(createBillDtoWithZeroPiecesNumber(),
                                                  "Pieces number has to be greater than zero.",
                                                  ErrorType.NUMBER_SHOULD_BE_POSITIVE);
        Arguments negativePiecesNumber = Arguments.of(createBillDtoWithNegativePiecesNumber(),
                                                      "Pieces number has to be greater than zero.",
                                                      ErrorType.NUMBER_SHOULD_BE_POSITIVE);

        return Stream.of(emptyClientId,
                         emptyRecords,
                         emptyRecordProductId,
                         emptyPiecesNumber,
                         zeroPiecesNumber,
                         negativePiecesNumber
        );
    }

}
