package piotrusha.e_shop.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.base.ListValidator;

import java.math.BigDecimal;
import java.util.List;

class DtoValidator {

    private final BillRepository billRepository;

    DtoValidator(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    Either<AppError, CreateBillDto> validateDto(CreateBillDto dto) {
        return validateClientId(dto.getClientId())
                .flatMap(x -> validateRecords(dto.getRecords()))
                .map(x -> dto);
    }

    Either<AppError, Bill> validateDto(BillActionDto dto) {
        return validateBillId(dto.getBillId());
    }

    private Either<AppError, Bill> validateBillId(BigDecimal billId) {
        if (billId == null) {
            return Either.left(AppError.validation("Bill id cannot be empty."));
        }
        return billRepository.findByBillId(billId)
                             .toEither(() -> AppError.notFound(String.format("Bill with billId %s not found.", billId)));
    }

    private Either<AppError, BigDecimal> validateClientId(BigDecimal clientId) {
        if (clientId == null) {
            return Either.left(AppError.validation("Client id cannot be empty."));
        }
        // TODO validate client existence (ClientFacade)
        return Either.right(clientId);
    }

    private Either<AppError, List<CreateBillDto.CreateBillRecordDto>> validateRecords(List<CreateBillDto.CreateBillRecordDto> dtos) {
        if (dtos == null || dtos.isEmpty()) {
            return Either.left(AppError.validation("Bill records cannot be empty."));
        }
        return ListValidator.checkError(dtos, this::validateRecord)
                            .map(x -> dtos);
    }

    private Either<AppError, CreateBillDto.CreateBillRecordDto> validateRecord(CreateBillDto.CreateBillRecordDto dto) {
        return validatePiecesNumber(dto.getPiecesNumber())
                .flatMap(x -> validateProductId(dto.getProductId()))
                .map(x -> dto);
    }

    private Either<AppError, Integer> validatePiecesNumber(Integer piecesNumber) {
        if (piecesNumber == null || piecesNumber <= 0) {
            return Either.left(AppError.validation("Pieces number has to be greater than zero."));
        }
        return Either.right(piecesNumber);
    }

    private Either<AppError, BigDecimal> validateProductId(BigDecimal productId) {
        if (productId == null) {
            return Either.left(AppError.validation("Product id cannot be empty."));
        }
        return Either.right(productId);
    }

}
