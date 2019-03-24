package piotrusha.e_shop.bill.domain;

import io.vavr.control.Either;
import io.vavr.control.Try;
import piotrusha.e_shop.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.bill.domain.dto.BillDto;
import piotrusha.e_shop.bill.domain.dto.CreateBillDto;
import piotrusha.e_shop.base.AppError;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

    public Either<AppError, BillDto> cancelBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billCanceller::cancelBill)
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

    public Either<AppError, BillDto> payBill(BillActionDto billActionDto) {
        return dtoValidator.validateDto(billActionDto)
                           .flatMap(billPayer::payBill)
                           .peek(billRepository::save)
                           .map(Bill::toDto);
    }

    public Either<AppError, BillDto>  findBillByBillId(BigDecimal billId) {
        return billRepository.findByBillId(billId)
                             .toEither(() -> AppError.notFound(String.format("Bill with billId %s not found.", billId)))
                             .map(Bill::toDto);
    }

    public List<BillDto> findBillsByClientId(BigDecimal clientId) {
        return billRepository.findBillByClientId(clientId)
                             .stream()
                             .map(Bill::toDto)
                             .collect(Collectors.toList());
    }

    public Either<AppError, List<BillDto>> findBillsByClientIdAndBillState(BigDecimal clientId, String billState) {
        return Try.of(() -> BillState.valueOf(billState))
                  .toEither(() -> AppError.validation("Wrong bill state: " + billState))
                  .map(enumState -> findBillsByClientIdAndBillState(clientId, enumState));
    }

    private List<BillDto> findBillsByClientIdAndBillState(BigDecimal clientId, BillState billState) {
        return billRepository.findBillByClientIdAndBillState(clientId, billState)
                      .stream()
                      .map(Bill::toDto)
                      .collect(Collectors.toList());
    }

}
