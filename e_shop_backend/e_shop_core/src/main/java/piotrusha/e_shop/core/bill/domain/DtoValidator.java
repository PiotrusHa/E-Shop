package piotrusha.e_shop.core.bill.domain;

import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto.CreateBillRecordDto;
import piotrusha.e_shop.core.bill.domain.exception.BillValidationException;

import java.math.BigDecimal;
import java.util.List;

class DtoValidator {

    void validateDto(CreateBillDto dto) {
        validateClientId(dto.getClientId());
        validateRecords(dto.getRecords());
    }

    private void validateClientId(BigDecimal clientId) {
        if (clientId == null) {
            throw BillValidationException.emptyClientId();
        }
        // TODO validate client existence (ClientFacade)
    }

    private void validateRecords(List<CreateBillRecordDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            throw BillValidationException.emptyRecords();
        }
        dtos.forEach(this::validateRecord);
    }

    private void validateRecord(CreateBillRecordDto dto) {
        validatePiecesNumber(dto.getPiecesNumber());
        validateProductId(dto.getProductId());
    }

    private void validatePiecesNumber(Integer piecesNumber) {
        if (piecesNumber == null || piecesNumber <= 0) {
            throw BillValidationException.wrongPiecesNumber();
        }
    }

    private void validateProductId(BigDecimal productId) {
        if (productId == null) {
            throw BillValidationException.emptyProductId();
        }
    }

}
