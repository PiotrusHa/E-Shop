package piotrusha.e_shop.bill.domain;

import io.vavr.control.Either;
import io.vavr.control.Try;
import piotrusha.e_shop.base.AppError;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;

import java.math.BigDecimal;
import java.util.List;

public class BillFacade {

    private final BillCreator billCreator;
    private final BillCanceller billCanceller;
    private final BillPayer billPayer;

    private final BillRepository billRepository;

    private final DtoValidator dtoValidator;

    public BillFacade(BillCreator billCreator, BillCanceller billCanceller, BillPayer billPayer, BillRepository billRepository,
                      DtoValidator dtoValidator) {
        this.billCreator = billCreator;
        this.billCanceller = billCanceller;
        this.billPayer = billPayer;
        this.billRepository = billRepository;
        this.dtoValidator = dtoValidator;
    }

    public Either<AppError, BillDto> createBill(CreateBillDto createBillDto) {
        return dtoValidator.validateDto(createBillDto)
                           .flatMap(billCreator::createBill)
                           .map(Bill::toDto)
                           .peek(billRepository::add);
    }

    public Either<AppError, BillDto> cancelBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billCanceller::cancelBill)
                           .map(Bill::toDto)
                           .peek(billRepository::update);
    }

    public Either<AppError, BillDto> payBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billPayer::payBill)
                           .map(Bill::toDto)
                           .peek(billRepository::update);
    }

    public Either<AppError, BillDto> findBillByBillId(BigDecimal billId) {
        return billRepository.findByBillId(billId)
                             .toEither(() -> AppError.billNotFound(billId));
    }

    public List<BillDto> findBillsByClientId(BigDecimal clientId) {
        return billRepository.findBillByClientId(clientId);
    }

    public Either<AppError, List<BillDto>> findBillsByClientIdAndBillState(BigDecimal clientId, String billState) {
        return Try.of(() -> BillState.valueOf(billState))
                  .toEither(() -> AppError.wrongBillState(billState))
                  .map(enumState -> findBillsByClientIdAndBillState(clientId, enumState));
    }

    private List<BillDto> findBillsByClientIdAndBillState(BigDecimal clientId, BillState billState) {
        return billRepository.findBillByClientIdAndBillState(clientId, billState.toString());
    }

}
