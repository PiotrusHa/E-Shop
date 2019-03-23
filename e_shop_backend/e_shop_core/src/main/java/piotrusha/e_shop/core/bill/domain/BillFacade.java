package piotrusha.e_shop.core.bill.domain;

import io.vavr.control.Either;
import piotrusha.e_shop.core.base.AppError;
import piotrusha.e_shop.core.bill.domain.dto.BillActionDto;
import piotrusha.e_shop.core.bill.domain.dto.BillDto;
import piotrusha.e_shop.core.bill.domain.dto.CreateBillDto;

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

    public List<BillDto> findBillsByClientIdAndBillState(BigDecimal clientId, String billState) {
        return billRepository.findBillByClientIdAndBillState(clientId, BillState.valueOf(billState))
                             .stream()
                             .map(Bill::toDto)
                             .collect(Collectors.toList());
    }

}
